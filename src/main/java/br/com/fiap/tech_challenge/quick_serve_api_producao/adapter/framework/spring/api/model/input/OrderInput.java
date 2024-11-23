package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.api.model.input;

import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.api.model.input.OrderPortInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInput implements OrderPortInput {

    private Long orderId;

}
