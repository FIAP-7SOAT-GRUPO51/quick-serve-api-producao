package br.com.fiap.tech_challenge.quick_serve_api_producao.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(Long id) {
        this(String.format("Pedido de id %d não existe", id));
    }
}