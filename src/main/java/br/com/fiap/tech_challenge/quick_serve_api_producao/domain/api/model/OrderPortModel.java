package br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model;

import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;

public interface OrderPortModel {
    Long getOrderId();
    void setOrderId(Long orderId);
    OrderStatus getStatus();
    void setStatus(OrderStatus status);
}
