package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.service;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.model.OrderSpringJpaAdapter;
import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.repository.OrderRepositorySpringJpaAdapter;
import br.com.fiap.tech_challenge.quick_serve_api_producao.api.assembler.OrderPortAssembler;
import br.com.fiap.tech_challenge.quick_serve_api_producao.api.model.OrderPortModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.exception.EntityInUseException;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.exception.OrderNotFoundException;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.service.OrderServicePort;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceSpringAdapter implements OrderServicePort {

    @Autowired
    private OrderRepositorySpringJpaAdapter orderRepositorySpringJpaAdapter;

    @Autowired
    private OrderPortAssembler orderPortAssembler;

    @Transactional
    @Override
    public OrderPort save(OrderPort order) {

        try {
            return orderRepositorySpringJpaAdapter.save(
                    OrderSpringJpaAdapter.builder()
                            .orderId(order.getOrderId())
                            .id(order.getId())
                            .status(order.getStatus())
                            .build()
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("Integridade de dados violada -> %s", e.getMessage()));
        }

    }

    public Page<OrderPortModel> listAllOrders(Pageable pageable) {
        Page<OrderSpringJpaAdapter> orderRepositorySpringJpaAdapterAll = orderRepositorySpringJpaAdapter.findAll(pageable);
        List<OrderPortModel> orderPortList = orderPortAssembler.toCollectionModel(orderRepositorySpringJpaAdapterAll.toList());
        return new PageImpl<>(orderPortList, pageable, orderRepositorySpringJpaAdapterAll.getTotalElements());
    }

    public OrderPort findByOrderId(Long orderId) {
        return orderRepositorySpringJpaAdapter.findByOrderId(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
