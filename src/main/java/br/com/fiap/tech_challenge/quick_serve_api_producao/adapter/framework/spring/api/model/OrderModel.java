package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.api.model;

import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.OrderPortModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderModel implements OrderPortModel {
    private Long orderId;
    private OrderStatus status;
}
