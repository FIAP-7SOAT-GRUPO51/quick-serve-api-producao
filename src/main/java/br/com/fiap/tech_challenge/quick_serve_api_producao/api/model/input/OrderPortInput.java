package br.com.fiap.tech_challenge.quick_serve_api_producao.api.model.input;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPortInput {

    @Positive
    private Long orderId;

}
