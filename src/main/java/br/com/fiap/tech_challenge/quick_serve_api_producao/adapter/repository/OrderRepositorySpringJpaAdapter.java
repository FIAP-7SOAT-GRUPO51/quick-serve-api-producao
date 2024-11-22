package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.repository;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.model.OrderSpringJpaAdapter;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.repository.port.OrderRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderRepositorySpringJpaAdapter implements OrderRepositoryPort {

    @Autowired
    private OrderRepositorySpringJpaAdapterInterface orderRepositorySpringJpaAdapterInterface;

    @Override
    public Optional<OrderPort> findByOrderId(Long id) {
        Optional<OrderSpringJpaAdapter> optionalOrderSpringJpaAdapter = orderRepositorySpringJpaAdapterInterface.findByOrderId(id);
        Optional<OrderPort> orderPort = null;

        if ( optionalOrderSpringJpaAdapter.isPresent() )
            orderPort = Optional.of(optionalOrderSpringJpaAdapter.get());

        return orderPort;
    }

    public Page<OrderSpringJpaAdapter> findAll(Pageable pageable) {
        return orderRepositorySpringJpaAdapterInterface.findAll(pageable);
    }

    public OrderSpringJpaAdapter save(OrderSpringJpaAdapter orderSpringJpaAdapter) {
        return orderRepositorySpringJpaAdapterInterface.save(orderSpringJpaAdapter);
    }
}
