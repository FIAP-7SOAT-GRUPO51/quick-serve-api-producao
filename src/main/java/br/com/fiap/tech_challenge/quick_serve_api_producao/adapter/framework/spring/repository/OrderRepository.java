package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.repository;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.model.Order;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.assembler.OrderPortAssembler;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.OrderPortModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.srping.repository.OrderRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository implements OrderRepositoryPort {

    @Autowired
    private OrderRepositoryInterface orderRepositoryInterface;

    @Autowired
    OrderPortAssembler orderPortAssembler;

    @Override
    public Optional<OrderPort> findByOrderId(Long id) {
        Optional<Order> optionalOrderSpringJpaAdapter = orderRepositoryInterface.findByOrderId(id);
        Optional<OrderPort> orderPort = null;

        if ( optionalOrderSpringJpaAdapter.isPresent() ) {
            orderPort = Optional.of(optionalOrderSpringJpaAdapter.get());
        }

        return orderPort;
    }

    @Override
    public Page<OrderPortModel> findAll(Pageable pageable) {
        Page<Order> orderSpringJpaAdapters = orderRepositoryInterface.findAll(pageable);
        List<OrderPort> orderPortList = new ArrayList<>();
        orderPortList.addAll(orderSpringJpaAdapters.getContent());
        List<OrderPortModel> orderPortModelList = orderPortAssembler.toCollectionModel(orderPortList);
        Page<OrderPortModel> orderPortModelPage = new PageImpl<>(orderPortModelList, pageable,orderSpringJpaAdapters.getTotalElements());
        return orderPortModelPage;
    }

    @Override
    public OrderPort save(OrderPort orderPort) {
        OrderPort orderPortSaved = orderRepositoryInterface.save(Order.builder()
                .orderId(orderPort.getOrderId())
                .status(orderPort.getStatus())
                .id(orderPort.getId())
                .build());

        return orderPortSaved;
    }
}
