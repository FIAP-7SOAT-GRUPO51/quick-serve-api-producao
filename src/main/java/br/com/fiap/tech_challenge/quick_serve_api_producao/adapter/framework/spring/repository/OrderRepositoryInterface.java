package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.repository;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryInterface extends JpaRepository<Order, UUID> {

    Optional<Order> findByOrderId(Long orderId);
}
