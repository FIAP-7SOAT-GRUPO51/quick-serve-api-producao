package br.com.fiap.tech_challenge.quick_serve_api_producao.domain.exception;

public class EntityInUseException extends DomainException {

    private static final long serialVersionUID = 1L;

    public EntityInUseException(String message) {
        super(message);
    }

}