package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.srping.repository;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.model.Order;
import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.repository.OrderRepository;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {
    "spring.flyway.locations=classpath:db/migration/h2"
})
@TestPropertySource("/application-development.yml")
public class OrderRepositoryIT {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void permiteCriarTabela() {
        var totalRows = orderRepository.count();
        assertThat(totalRows).isNotNegative();
    }

    @Test
    void devePermitirRegistrarPedidoAutomaticamenteComStatusRecebido_QuandoForUmPedidoQueNaoExiste() {

        // Arrange
        var order = generateOrder();

        // Act
        var orderSaved = orderRepository.save(order);

        // Assert
        assertThat(orderSaved).isInstanceOf(OrderPort.class).isNotNull();
        assertThat(orderSaved.getOrderId()).isEqualTo(1L);
        assertThat(orderSaved.getStatus()).isEqualTo(OrderStatus.RECEBIDO);


    }

    private static Order generateOrder() {
        return Order.builder()
                .orderId(1L)
                .status(OrderStatus.RECEBIDO)
                .id(UUID.randomUUID())
                .build();
    }

}
