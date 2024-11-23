package br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.assembler;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.model.Order;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.input.OrderPortInput;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.core.ModelMapperConfig;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import org.modelmapper.ModelMapper;

public class OrderPortInputDisassembler {

    private final ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
    private final ModelMapper modelMapper = modelMapperConfig.modelMapper();

    public Order toDomainObject(OrderPortInput orderPortInput) {
        return modelMapper.map(orderPortInput, Order.class);
    }

    public void copyToDomainObject(OrderPortInput orderPortInput, OrderPort orderPort) {
        modelMapper.map(orderPortInput, orderPort);
    }

}