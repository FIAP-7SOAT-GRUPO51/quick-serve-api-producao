package br.com.fiap.tech_challenge.quick_serve_api_producao.api.model;

import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPortModel {
    private Long orderId;
    private OrderStatus status;
}
