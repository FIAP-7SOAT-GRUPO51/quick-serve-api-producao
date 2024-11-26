package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.repository;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.model.Order;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.assembler.OrderPortAssembler;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.OrderModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestPropertySource("/application-development.yml")
public class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    OrderPortAssembler orderPortAssembler;

    AutoCloseable openMock;

    @BeforeEach
    void setup() {
        openMock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMock.close();
    }

    @Test
    void testSave() {

        // Arrange
        var order = Order.builder()
                    .orderId(1L)
                    .build();

        var orderReturn = generateOrder();

        when(orderRepository.save(any(Order.class))).thenReturn(orderReturn);

        // Act
        var orderSaved = orderRepository.save(order);

        // Assert
        assertThat(orderSaved)
                .isNotNull()
                .isEqualTo(orderReturn);

        verify(orderRepository, times(1)).save(any(Order.class));

    }

    @Test
    void testFindByOrderId_WhenOrderExists() {

        // Arrange

        OrderPort order = generateOrder();
        var orderReturn = Optional.of(order);
        when(orderRepository.findByOrderId(any(Long.class))).thenReturn(orderReturn);

        // Act
        var orderSearch = orderRepository.findByOrderId(1L);

        // Assert
        assertThat(orderSearch)
                .isPresent()
                .containsSame(order);

        orderSearch.ifPresent( orderPort -> {
            assertThat(orderPort.getId()).isEqualTo(order.getId());
        });

        verify(orderRepository, times(1)).findByOrderId(any(Long.class));
    }

    @Test
    void testFindByOrderId_WhenOrderNotExists() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findByOrderId(orderId))
                .thenReturn(Optional.empty());

        // Act
        Optional<OrderPort> result = orderRepository.findByOrderId(orderId);

        // Assert
        assertTrue(result.isEmpty());
        verify(orderRepository).findByOrderId(orderId);
    }

    @Test
    void testFindAll() {

        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        OrderModel orderModel1 = new OrderModel();
        orderModel1.setOrderId(1L);
        orderModel1.setStatus(OrderStatus.RECEBIDO);

        OrderModel orderModel2 = new OrderModel();
        orderModel2.setOrderId(2L);
        orderModel2.setStatus(OrderStatus.RECEBIDO);

        List<OrderModel> expectedOrderModels = Arrays.asList(
                orderModel1,
                orderModel2
        );
        Page<OrderModel> orderPageModel = new PageImpl<>(expectedOrderModels, pageable, expectedOrderModels.size());


        // Mock repository call
        when(orderRepository.findAll(pageable)).thenReturn(orderPageModel);

        // Mock assembler conversion
        when(orderPortAssembler.toCollectionModel(anyList())).thenReturn(expectedOrderModels);

        // Act
        Page<OrderModel> result = orderRepository.findAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalElements());
        assertEquals(0, result.getNumber());
        assertEquals(10, result.getSize());

        verify(orderRepository, times(1)).findAll(pageable);

    }

    @Test
    void testCount() {
        // Arrange
        when(orderRepository.count()).thenReturn(10L);

        // Act
        Long count = orderRepository.count();

        // Assert
        assertEquals(10L, count);
        verify(orderRepository).count();
    }

    private static Order generateOrder() {
        return Order.builder()
                .orderId(1L)
                .status(OrderStatus.RECEBIDO)
                .id(UUID.randomUUID())
                .build();
    }

}
