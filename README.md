CASE: Realizar Transferências e Tarifar transferências

Demanda do negócio:
- O time de negócios solicitou uma API centralizada onde será feito a realização de transferências de valores entre contas.
- A API deve receber Tipos de Transferências sendo PIX e TED.
- Considerar que teremos uma alta volumetria de requisição para realização de transferências
- O time de negócios também solicitou uma API para consultar transferências realizadas com base na conta origem
- Considerar que teremos muitos clientes, ao mesmo tempo, consultando suas transferências e cada um deve ver apenas suas transferências
- O time de negócios também alertou que as transferências deverão ser tarifadas de acordo com o tipo da transferência
  - Para PIX o valor da tarifa deverá sempre 0 significando isenção 
  - Para TED acima de 100 reais a tarifa deverá ser 1% do valor da transferência, para as demais deverá ser 0 também siginifcando isenção
- Não há a necessidade de gerenciamento de cadastro de Conta, apenas receberemos o id da conta origem e da conta destino
 
Parte Técnica
- Utilizar Java + Spring
- Considerar a utilização da AWS para o desenvolvimento (podendo usar o localstack)
- Desenvolver testes unitários e teste de integração
- Projeto deverá ser entregue via link do repositório do github
