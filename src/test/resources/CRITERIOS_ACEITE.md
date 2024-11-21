# Critérios de aceite do microserviço
Esta definição é importante para definir as regras de negócio. Também servirá para orientar a cobertura de testes do microserviço de gestão da produção do pedido

## 1. Recepção do Pedido
1.1 Todo pedido recepcionado, deve nascer com o status <b>RECEBIDO</b>

1.2 Se for enviado um pedido que já existe na fila, a API deve retornar que o pedido já existe na fila

## 2. Preparação do Pedido
2.1 Ao iniciar a preparação do Pedido, deve ser informado ao sistema. Para isso, será utilizado uma rota específica, passando o identificador do pedido como parâmetro, para que a API altere o status para <b>EM PREPARAÇÃO</b>

2.2 O pedido somente pode entrar no status <b>EM PREPARAÇÃO</b>, se estiver no status <b>RECEBIDO</b>, caso contrário, deverá retornar uma mensagem de validação dizendo que esta operação é inválida

## 3. Pedido Pronto
3.1 Ao finalizar o preparo do pedido, deve ser informado ao sistema. Para isso, será utilizado uma rota específica, passando o identificador do pedido como parâmetro, para que a alteração do status seja efetivada.

3.2 Somente permitir a alteração do status para <b>PRONTO</b>, caso o status atual seja <b>EM PREPARAÇÃO</b>. Caso contrário, deverá retornar uma mensagem de validação dizendo que esta operação é inválida

## 4. Pedido Finalizado
4.1 Após a retirada do pedido do balcão, deve ser informado ao sistema que o pedido foi finalizado. Para isso, será utilizado uma rota específica, passando o identificador do pedido como parâmetro, para que a alteração do status seja efetivada para o status <b>FINALIZADO</b>. 

4.2 Somente permitir a alteração do status para <b>FINALIZADO</b>, caso o status atual seja <b>PRONTO</b>. Caso contrário, deverá retornar uma mensagem de validação dizendo que esta operação é inválida