package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.api.controller;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.api.OrderController;
import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.api.model.input.OrderInput;
import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.model.Order;
import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.service.OrderService;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.assembler.OrderPortAssembler;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.assembler.OrderPortInputDisassembler;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.OrderModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderPortAssembler orderPortAssembler;

    @Mock
    private OrderPortInputDisassembler orderSpringJpaAdapterInputDisassembler;

    @InjectMocks
    private OrderController orderController;

    AutoCloseable openMocks;

    private OrderPort mockOrderPort;
    private OrderModel mockOrderModel;
    private OrderInput mockOrderInput;

    @BeforeEach
    void setUp() {

        openMocks = MockitoAnnotations.openMocks(this);

        // Initialize mock objects
        mockOrderPort = generateOrder(3L);
        mockOrderPort.setId(UUID.randomUUID());
        mockOrderPort.setStatus(OrderStatus.RECEBIDO);

        mockOrderModel = new OrderModel();
        mockOrderModel.setOrderId(mockOrderPort.getOrderId());
        mockOrderModel.setStatus(mockOrderPort.getStatus());

        mockOrderInput = new OrderInput();
        mockOrderInput.setOrderId(5L);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testListAllOrders() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<OrderModel> orderPortPage = new PageImpl<>(Collections.singletonList(mockOrderModel));

        when(orderService.listAllOrders(pageable)).thenReturn(orderPortPage);
        when(orderPortAssembler.toModel(any(OrderPort.class))).thenReturn(mockOrderModel);

        // Act
        Page<OrderModel> result = orderController.list(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(orderService).listAllOrders(pageable);
    }

    @Test
    void testAddOrder() {
        // Arrange
        when(orderSpringJpaAdapterInputDisassembler.toDomainObject(mockOrderInput)).thenReturn(generateOrder(1L));
        when(orderService.save(any(OrderPort.class))).thenReturn(mockOrderPort);
        when(orderPortAssembler.toModel(mockOrderPort)).thenReturn(mockOrderModel);

        // Act
        OrderModel result = orderController.add(mockOrderInput);

        // Assert
        assertNotNull(result);
        assertEquals(mockOrderPort.getOrderId(), result.getOrderId());
        assertEquals(OrderStatus.RECEBIDO, result.getStatus());
        verify(orderService).save(any(OrderPort.class));
    }

    @Test
    void testStartsOrderPreparation() {
        // Arrange
        Long orderId = 1L;
        mockOrderPort.setId(UUID.randomUUID());

        when(orderService.findByOrderId(orderId)).thenReturn(mockOrderPort);

        // Act
        orderController.startsOrderPreparation(orderId);

        // Assert
        assertEquals(OrderStatus.EM_PREPARACAO, mockOrderPort.getStatus());
        verify(orderService).findByOrderId(orderId);
        verify(orderService).save(mockOrderPort);
    }

    @Test
    void testOrderDone() {
        // Arrange
        Long orderId = 1L;
        mockOrderPort.setId(UUID.randomUUID());

        when(orderService.findByOrderId(orderId)).thenReturn(mockOrderPort);

        // Act
        orderController.orderDone(orderId);

        // Assert
        assertEquals(OrderStatus.PRONTO, mockOrderPort.getStatus());
        verify(orderService).findByOrderId(orderId);
        verify(orderService).save(mockOrderPort);
    }

    @Test
    void testOrderFinished() {
        // Arrange
        Long orderId = 1L;
        mockOrderPort.setId(UUID.randomUUID());

        when(orderService.findByOrderId(orderId)).thenReturn(mockOrderPort);

        // Act
        orderController.orderFinished(orderId);

        // Assert
        assertEquals(OrderStatus.FINALIZADO, mockOrderPort.getStatus());
        verify(orderService).findByOrderId(orderId);
        verify(orderService).save(mockOrderPort);
    }

    @Test
    void testFindByOrderId() {
        // Arrange
        Long orderId = 1L;

        // The current implementation returns null, so we'll test that
        OrderModel result = orderController.findByOrderId(orderId);

        // Assert
        assertNull(result);
    }

    private static Order generateOrder(Long orderId) {
        return Order.builder()
                .orderId(orderId)
                .status(OrderStatus.RECEBIDO)
                .id(UUID.randomUUID())
                .build();
    }

}
