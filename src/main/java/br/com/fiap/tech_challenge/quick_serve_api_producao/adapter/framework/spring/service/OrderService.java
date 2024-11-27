package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.service;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.repository.OrderRepository;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.assembler.OrderPortAssembler;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.OrderModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.exception.OrderNotFoundException;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.service.OrderServicePort;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements OrderServicePort {

    @Autowired
    private OrderRepository orderRepository;

    private final OrderPortAssembler orderPortAssembler = new OrderPortAssembler();

    @Transactional
    @Override
    public OrderPort save(OrderPort order) {
        return orderRepository.save(order);
    }

    public Page<OrderModel> listAllOrders(Pageable pageable) {
        Page<OrderModel> orderPortModelPage = orderRepository.findAll(pageable);
        return orderPortModelPage;
    }

    @Override
    public OrderPort findByOrderId(Long orderId) {
        return orderRepository.findByOrderId(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
