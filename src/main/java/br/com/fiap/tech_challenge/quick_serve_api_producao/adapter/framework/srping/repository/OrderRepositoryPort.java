package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.srping.repository;

import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.OrderPortModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderRepositoryPort  {

    Optional<OrderPort> findByOrderId(Long orderId);
    Page<OrderPortModel> findAll(Pageable pageable);
    OrderPort save(OrderPort orderPort);
    Long count();
}
