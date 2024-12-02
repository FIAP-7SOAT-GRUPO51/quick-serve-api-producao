package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.bdd;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.model.Order;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class StepDefinition {

    private Response response;
    private Order orderResponse;
    private final String ENDPOINT_API = "http://localhost:8088/v1/order";

    @Quando("um pedido é criado na fila da cozinha")
    public void um_pedido_é_criado_na_fila_da_cozinha() {

        // Prepare order input
        String orderInput = """
        {
            "orderId": 46
        }
        """;

        response = given()
                    .contentType(ContentType.JSON)
                    .body(orderInput)
                    .when()
                    .post(ENDPOINT_API);
    }

    @Então("seu primeiro status deve ser RECEBIDO")
    public void seu_primeiro_status_deve_ser_recebido() {

        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body("orderId", notNullValue())
                .body("status", equalTo("RECEBIDO"));

    }

}
