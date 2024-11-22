package br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port;

import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;

import java.util.UUID;

public interface OrderPort {

    public UUID getId();
    public void setId(UUID id);

    public Long getOrderId();
    public void setOrderId(Long orderId);

    public OrderStatus getStatus();
    public void setStatus(OrderStatus status);

}
