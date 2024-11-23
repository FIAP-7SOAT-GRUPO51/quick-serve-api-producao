package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.srping.repository;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.model.Order;
import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.repository.OrderRepository;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@TestPropertySource("/application-development.yml")
public class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

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
    void devePermitirRegistrarPedidoAutomaticamenteComStatusRecebido_QuandoForUmPedidoQueNaoExiste() {

        // Arrange
        var order = Order.builder()
                    .orderId(1L)
                    .build();

        var orderReturn = Order.builder()
                .orderId(1L)
                .status(OrderStatus.RECEBIDO)
                .id(UUID.randomUUID())
                .build();

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
    void deveRetornarMensagemDeValidacao_QuandoEnviarInclusaoDePedidoQueJaExiste() {
        fail("Teste não implementado");
    }

}
