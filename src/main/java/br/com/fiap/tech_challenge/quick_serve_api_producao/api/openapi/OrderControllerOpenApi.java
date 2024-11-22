package br.com.fiap.tech_challenge.quick_serve_api_producao.api.openapi;

import br.com.fiap.tech_challenge.quick_serve_api_producao.api.model.OrderPortModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.api.model.input.OrderPortInput;
import br.com.fiap.tech_challenge.quick_serve_api_producao.api.openapi.model.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Produção", description = "Gerencia produção da lanchonete")
public interface OrderControllerOpenApi {

    @Operation(summary = "Lista pedidos")
    @PageableParameter
    Page<OrderPortModel> list(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Encontra pedido por ID")
    OrderPortModel findByOrderId(
            @Parameter(description = "ID do pedido", required = true)
            Long orderId);

    @Operation(summary = "Inclui pedido")
    OrderPortModel add(
            @RequestBody(description = "Informações para inclusão de Clientes", required = true)
            OrderPortInput orderPortInput);

    @Operation(summary = "Inicia preparação do pedido")
    void startsOrderPreparation(
            @Parameter(description = "ID do pedido", required = true)
            Long orderId);

    @Operation(summary = "Indica que o pedido está pronto")
    void orderDone(
            @Parameter(description = "ID do pedido", required = true)
            Long orderId);

    @Operation(summary = "Indica que o pedido está finalizado")
    void orderFinished(
            @Parameter(description = "ID do pedido", required = true)
            Long orderId);

}