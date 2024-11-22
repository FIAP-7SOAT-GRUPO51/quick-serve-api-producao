package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.model;

import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "order_app")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSpringJpaAdapter implements OrderPort {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long orderId;

}
