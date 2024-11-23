package br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.controller;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.api.model.input.OrderInput;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.OrderPortModel;

public interface ControllerPort {

    OrderPortModel findByOrderId(Long orderId);
    OrderPortModel add(OrderInput orderInput);
    void startsOrderPreparation(Long orderId);
    void orderDone(Long orderId);
    void orderFinished(Long orderId);

}
