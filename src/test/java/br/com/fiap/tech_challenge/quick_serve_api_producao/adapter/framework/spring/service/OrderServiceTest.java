package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.service;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.repository.OrderRepository;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.OrderModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.exception.EntityInUseException;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.exception.OrderNotFoundException;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    AutoCloseable openMocks;

    private OrderPort mockOrder;
    private OrderModel mockOrderModel;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        mockOrder = mock(OrderPort.class);
        mockOrderModel = mock(OrderModel.class);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testSaveOrder_Success() {
        // Arrange
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

        // Act
        OrderPort savedOrder = orderService.save(mockOrder);

        // Assert
        assertNotNull(savedOrder);
        verify(orderRepository).save(mockOrder);
    }

    @Test
    void testSaveOrder_DataIntegrityViolation() {
        // Arrange
        when(orderRepository.save(mockOrder))
                .thenThrow(new DataIntegrityViolationException("Duplicate key"));

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            orderService.save(mockOrder);
        });
    }

    @Test
    void testListAllOrders_Success() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<OrderModel> mockPage = new PageImpl<>(Collections.singletonList(mockOrderModel));

        when(orderRepository.findAll(pageable)).thenReturn(mockPage);

        // Act
        Page<OrderModel> resultPage = orderService.listAllOrders(pageable);

        // Assert
        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        verify(orderRepository).findAll(pageable);
    }

    @Test
    void testFindByOrderId_Success() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findByOrderId(orderId)).thenReturn(Optional.of(mockOrder));

        // Act
        OrderPort foundOrder = orderService.findByOrderId(orderId);

        // Assert
        assertNotNull(foundOrder);
        verify(orderRepository).findByOrderId(orderId);
    }

    @Test
    void testFindByOrderId_NotFound() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrderNotFoundException.class, () -> {
            orderService.findByOrderId(orderId);
        });
    }

}
