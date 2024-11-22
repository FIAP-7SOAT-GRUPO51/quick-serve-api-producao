package br.com.fiap.tech_challenge.quick_serve_api_producao.api.assembler;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.model.OrderSpringJpaAdapter;
import br.com.fiap.tech_challenge.quick_serve_api_producao.api.model.input.OrderPortInput;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.port.OrderPort;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderPortInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public OrderSpringJpaAdapter toDomainObject(OrderPortInput orderPortInput) {
        return modelMapper.map(orderPortInput, OrderSpringJpaAdapter.class);
    }

    public void copyToDomainObject(OrderPortInput orderPortInput, OrderPort orderPort) {
        modelMapper.map(orderPortInput, orderPort);
    }

}