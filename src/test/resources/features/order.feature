# language: pt

Funcionalidade: Pedido

  Cenario: Registrar pedido
    Quando um pedido é criado na fila da cozinha
    Então seu primeiro status deve ser RECEBIDO