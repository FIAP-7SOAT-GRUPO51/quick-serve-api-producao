package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.api;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.api.model.input.OrderInput;
import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.api.openapi.OrderControllerOpenApi;
import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.service.OrderService;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.assembler.OrderPortAssembler;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.assembler.OrderPortInputDisassembler;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.OrderPortModel;
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
    private OrderService orderService;

    private OrderPortAssembler orderPortAssembler = new OrderPortAssembler();

    @GetMapping
    @Override
    public Page<OrderPortModel> list(Pageable pageable) {
        return orderService.listAllOrders(pageable);
    }

    @Override
    public OrderPortModel findByOrderId(Long orderId) {
        return null;
    }

    private OrderPortInputDisassembler orderSpringJpaAdapterInputDisassembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public OrderPortModel add(@RequestBody @Valid OrderInput orderInput) {
        OrderPort orderPort = orderSpringJpaAdapterInputDisassembler.toDomainObject(orderInput);
        orderPort.setId(UUID.randomUUID());
        orderPort.setStatus(OrderStatus.RECEBIDO);
        OrderPort orderPortSaved = orderService.save(orderPort);
        return orderPortAssembler.toModel(orderPortSaved);
    }

    @PutMapping("/{orderId}/start")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void startsOrderPreparation(@PathVariable Long orderId) {
        OrderPort orderPort = orderService.findByOrderId(orderId);
        orderPort.setStatus(OrderStatus.EM_PREPARACAO);
        orderService.save(orderPort);
    }

    @PutMapping("/{orderId}/done")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void orderDone(@PathVariable Long orderId) {
        OrderPort orderPort = orderService.findByOrderId(orderId);
        orderPort.setStatus(OrderStatus.PRONTO);
        orderService.save(orderPort);
    }

    @PutMapping("/{orderId}/finished")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void orderFinished(@PathVariable Long orderId) {
        OrderPort orderPort = orderService.findByOrderId(orderId);
        orderPort.setStatus(OrderStatus.FINALIZADO);
        orderService.save(orderPort);
    }
}