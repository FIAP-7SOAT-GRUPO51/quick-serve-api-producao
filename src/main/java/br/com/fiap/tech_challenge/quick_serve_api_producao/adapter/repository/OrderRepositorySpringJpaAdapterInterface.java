package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.repository;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.model.OrderSpringJpaAdapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepositorySpringJpaAdapterInterface extends JpaRepository<OrderSpringJpaAdapter, UUID> {

    Optional<OrderSpringJpaAdapter> findByOrderId(Long orderId);
}
