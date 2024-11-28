package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.repository;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.model.Order;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.assembler.OrderPortAssembler;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.OrderModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
    "spring.flyway.locations=classpath:db/migration/h2"
})
@TestPropertySource("/application-development.yml")
public class OrderRepositoryIT {

    @Autowired
    private OrderRepository orderRepository;

    @Mock
    OrderPortAssembler orderPortAssembler;


    @Test
    void permiteCriarTabela() {
        var totalRows = orderRepository.count();
        assertThat(totalRows).isNotNegative();
    }

    @Test
    void testSave() {

        // Arrange
        var order = Order.builder()
                .id(UUID.randomUUID())
                .orderId(1L)
                .status(OrderStatus.RECEBIDO)
                .build();

        // Act
        var orderSaved = orderRepository.save(order);

        // Assert
        assertThat(orderSaved)
                .isNotNull();

    }

    @Test
    void testFindByOrderId_WhenOrderExists() {

        // Arrange

        // Act
        var orderSearch = orderRepository.findByOrderId(2L);

        // Assert
        assertThat(orderSearch)
                .isPresent();

        orderSearch.ifPresent( orderPort -> {
            assertThat(orderPort.getOrderId()).isEqualTo(2L);
        });

    }

    @Test
    void testFindByOrderId_WhenOrderNotExists() {
        // Arrange
        Long orderId = 101278L;

        // Act
        Optional<OrderPort> result = orderRepository.findByOrderId(orderId);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAll() {

        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<OrderModel> result = orderRepository.findAll(pageable);

        // Assert
        assertNotNull(result);
        assertThat(result.getContent().size()).isNotNegative();

    }

    @Test
    void testCount() {
        // Arrange

        // Act
        Long count = orderRepository.count();

        // Assert
        assertThat(count).isNotNegative();
    }

    private static Order generateOrder() {
        return Order.builder()
                .orderId(1L)
                .status(OrderStatus.RECEBIDO)
                .id(UUID.randomUUID())
                .build();
    }

}
