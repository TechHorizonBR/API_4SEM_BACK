package br.fatec.bd4;

import br.fatec.bd4.entity.View;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/sql/usuarios/insert-usuarios.sql", "/sql/devices/insert-devices.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/devices/delete-devices.sql", "/sql/usuarios/delete-usuarios.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FilterControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void find_users_devices_return_listofusersanduser_status200(){
        List<View.ViewFilterUserDevice> responseBody =
                webTestClient.get()
                        .uri("/api/filters/userDevice")
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(View.ViewFilterUserDevice.class)
                        .returnResult()
                        .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotEmpty();

    }
}
