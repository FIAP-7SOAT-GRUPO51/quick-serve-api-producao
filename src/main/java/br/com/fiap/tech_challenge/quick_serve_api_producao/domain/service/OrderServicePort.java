package br.com.fiap.tech_challenge.quick_serve_api_producao.domain.service;

import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;

public interface OrderServicePort {

    OrderPort save(OrderPort order);

}
