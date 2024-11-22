package br.com.fiap.tech_challenge.quick_serve_api_producao.api.controller;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.model.OrderSpringJpaAdapter;
import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.service.OrderServiceSpringAdapter;
import br.com.fiap.tech_challenge.quick_serve_api_producao.api.assembler.OrderPortAssembler;
import br.com.fiap.tech_challenge.quick_serve_api_producao.api.assembler.OrderPortInputDisassembler;
import br.com.fiap.tech_challenge.quick_serve_api_producao.api.model.OrderPortModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.api.model.input.OrderPortInput;
import br.com.fiap.tech_challenge.quick_serve_api_producao.api.openapi.OrderControllerOpenApi;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController implements OrderControllerOpenApi {

    @Autowired
    private OrderServiceSpringAdapter orderServiceSpringAdapter;

    @Autowired
    private OrderPortInputDisassembler orderSpringJpaAdapterInputDisassembler;

    @Autowired
    private OrderPortAssembler orderPortAssembler;

    @GetMapping
    @Override
    public Page<OrderPortModel> list(Pageable pageable) {
        return orderServiceSpringAdapter.listAllOrders(pageable);
    }

    @Override
    public OrderPortModel findByOrderId(Long orderId) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public OrderPortModel add(@RequestBody @Valid OrderPortInput orderPortInput) {
        OrderPort orderPort = orderSpringJpaAdapterInputDisassembler.toDomainObject(orderPortInput);
        orderPort.setId(UUID.randomUUID());
        orderPort.setStatus(OrderStatus.RECEBIDO);
        orderPort = orderServiceSpringAdapter.save(orderPort);
        return orderPortAssembler.toModel(orderPort);
    }

    @PutMapping("/{orderId}/start")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void startsOrderPreparation(@PathVariable Long orderId) {
        OrderPort orderPort = orderServiceSpringAdapter.findByOrderId(orderId);
        orderPort.setStatus(OrderStatus.EM_PREPARACAO);
        orderServiceSpringAdapter.save(orderPort);
    }

    @PutMapping("/{orderId}/done")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void orderDone(@PathVariable Long orderId) {
        OrderPort orderPort = orderServiceSpringAdapter.findByOrderId(orderId);
        orderPort.setStatus(OrderStatus.PRONTO);
        orderServiceSpringAdapter.save(orderPort);
    }

    @PutMapping("/{orderId}/finished")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void orderFinished(@PathVariable Long orderId) {
        OrderPort orderPort = orderServiceSpringAdapter.findByOrderId(orderId);
        orderPort.setStatus(OrderStatus.FINALIZADO);
        orderServiceSpringAdapter.save(orderPort);
    }
}