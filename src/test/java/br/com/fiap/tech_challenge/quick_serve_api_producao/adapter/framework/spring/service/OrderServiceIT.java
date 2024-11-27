package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.service;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.model.Order;
import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.repository.OrderRepository;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.OrderModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.exception.DomainException;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.exception.EntityInUseException;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.exception.OrderNotFoundException;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "spring.flyway.locations=classpath:db/migration/h2"
})
@TestPropertySource("/application-development.yml")
public class OrderServiceIT {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testSaveOrder_Success() {
        // Arrange

        // Act
        OrderPort savedOrder = orderService.save(generateOrder(1L));

        // Assert
        assertNotNull(savedOrder);
    }

    @Test
    void testListAllOrders_Success() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<OrderModel> resultPage = orderService.listAllOrders(pageable);

        // Assert
        assertNotNull(resultPage);
        assertThat(resultPage.getContent().size()).isNotNegative();
    }

    @Test
    void testFindByOrderId_Success() {
        // Arrange
        Long orderId = 1L;

        // Act
        OrderPort foundOrder = orderService.findByOrderId(orderId);

        // Assert
        assertNotNull(foundOrder);
    }

    @Test
    void testFindByOrderId_NotFound() {
        // Arrange
        Long orderId = 145645L;

        // Act & Assert
        assertThrows(OrderNotFoundException.class, () -> {
            orderService.findByOrderId(orderId);
        });
    }

    private static Order generateOrder() {
        return Order.builder()
                .orderId(1L)
                .status(OrderStatus.RECEBIDO)
                .id(UUID.randomUUID())
                .build();
    }

    private static Order generateOrder(Long orderId) {
        return Order.builder()
                .orderId(orderId)
                .status(OrderStatus.RECEBIDO)
                .id(UUID.randomUUID())
                .build();
    }

}
