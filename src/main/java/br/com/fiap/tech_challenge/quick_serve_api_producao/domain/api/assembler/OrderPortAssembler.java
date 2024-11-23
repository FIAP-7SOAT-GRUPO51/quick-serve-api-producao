package br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.assembler;

import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.OrderPortModel;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.core.ModelMapperConfig;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPortAssembler {

    private final ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
    private final ModelMapper modelMapper = modelMapperConfig.modelMapper();

    public OrderPortModel toModel(OrderPort orderPort) {
        return modelMapper.map(orderPort, OrderPortModel.class);
    }

    public List<OrderPortModel> toCollectionModel(Collection<OrderPort> orderPorts) {
        return orderPorts.stream().map(this::toModel).collect(Collectors.toList());
    }

}