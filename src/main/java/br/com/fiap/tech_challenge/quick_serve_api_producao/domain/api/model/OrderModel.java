package br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model;

import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;

public class OrderModel {

    private Long orderId;
    private OrderStatus status;

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
