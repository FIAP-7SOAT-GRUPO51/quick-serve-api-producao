package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.api.openapi;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.api.model.input.OrderInput;
import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.api.openapi.model.PageableParameter;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.controller.ControllerPort;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.OrderModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Produção", description = "Gerencia produção da lanchonete")
public interface OrderControllerOpenApi extends ControllerPort {

    @Operation(summary = "Lista pedidos")
    @PageableParameter
    Page<OrderModel> list(@Parameter(hidden = true) Pageable pageable);

    @Override
    @Operation(summary = "Encontra pedido por ID")
    OrderModel findByOrderId(
            @Parameter(description = "ID do pedido", required = true)
            Long orderId);

    @Override
    @Operation(summary = "Inclui pedido")
    OrderModel add(
            @RequestBody(description = "Informações para inclusão de Clientes", required = true)
            OrderInput orderInput);

    @Override
    @Operation(summary = "Inicia preparação do pedido")
    void startsOrderPreparation(
            @Parameter(description = "ID do pedido", required = true)
            Long orderId);

    @Override
    @Operation(summary = "Indica que o pedido está pronto")
    void orderDone(
            @Parameter(description = "ID do pedido", required = true)
            Long orderId);

    @Override
    @Operation(summary = "Indica que o pedido está finalizado")
    void orderFinished(
            @Parameter(description = "ID do pedido", required = true)
            Long orderId);

}