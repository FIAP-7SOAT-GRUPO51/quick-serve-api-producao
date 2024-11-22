package br.com.fiap.tech_challenge.quick_serve_api_producao.domain.repository.port;

import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;

import java.util.Optional;

public interface OrderRepositoryPort {

    public Optional<OrderPort> findByOrderId(Long orderId);

}
