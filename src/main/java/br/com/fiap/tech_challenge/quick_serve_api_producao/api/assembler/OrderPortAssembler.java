package br.com.fiap.tech_challenge.quick_serve_api_producao.api.assembler;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.model.OrderSpringJpaAdapter;
import br.com.fiap.tech_challenge.quick_serve_api_producao.api.model.OrderPortModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderPortAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public OrderPortModel toModel(OrderPort orderPort) {
        return modelMapper.map(orderPort, OrderPortModel.class);
    }

    public List<OrderPortModel> toCollectionModel(Collection<OrderSpringJpaAdapter> orderSpringJpaAdapters) {
        return orderSpringJpaAdapters.stream()
                .map(orderPort -> toModel(orderPort)).collect(Collectors.toList());
    }

}