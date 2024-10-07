provider "aws" {
  access_key = "test"
  secret_key = "test"
  region     = "sa-east-1"

  s3_use_path_style           = true
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true

  endpoints {
    sqs            = "http://localhost:4566"
    sns            = "http://localhost:4566"
    dynamodb       = "http://localhost:4566"
  }
}

resource "aws_sqs_queue" "queue_tarifas" {
  name = "queue-tarifas"
}

resource "aws_sqs_queue" "queue_tarifas_dlq" {
  name = "queue-tarifas-dlq"
}

resource "aws_sns_topic" "topico_transferencias" {
  name = "topico-transferencias"
}

resource "aws_sns_topic_subscription" "queue_subscription" {
  topic_arn              = aws_sns_topic.topico_transferencias.arn
  protocol               = "sqs"
  endpoint               = aws_sqs_queue.queue_tarifas.arn
  endpoint_auto_confirms = true
}

resource "aws_sqs_queue_policy" "sqs_policy" {
  queue_url = aws_sqs_queue.queue_tarifas.id
  policy    = <<POLICY
  {
    "Version": "2012-10-17",
    "Statement": [
      {
        "Effect": "Allow",
        "Principal": "*",
        "Action": "SQS:SendMessage",
        "Resource": "${aws_sqs_queue.queue_tarifas.arn}",
        "Condition": {
          "ArnEquals": {
            "aws:SourceArn": "${aws_sns_topic.topico_transferencias.arn}"
          }
        }
      }
    ]
  }
  POLICY
}

resource "aws_sqs_queue_redrive_policy" "redrive_policy" {
  queue_url = aws_sqs_queue.queue_tarifas.id
  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.queue_tarifas_dlq.arn
    maxReceiveCount     = 2
  })
}

resource "aws_dynamodb_table" "tb_transacao" {
  name           = "tb_transacao"
  read_capacity  = 5
  write_capacity = 5

  attribute {
    name = "codigo_chave_particao"
    type = "S"
  }

  attribute {
    name = "codigo_chave_filtro"
    type = "S"
  }

  hash_key = "codigo_chave_particao"
  range_key = "codigo_chave_filtro"

  attribute {
    name = "id_conta_origem"
    type = "S"
  }

  global_secondary_index {
    name            = "gsi_transferencia_conta_origem"
    hash_key        = "id_conta_origem"
    projection_type = "ALL"
    read_capacity   = 5
    write_capacity  = 5
  }
}