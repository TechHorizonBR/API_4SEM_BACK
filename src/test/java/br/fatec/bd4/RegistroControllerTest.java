package br.fatec.bd4;

import br.fatec.bd4.web.dto.RegistersResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TechHorizonApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RegistroControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void mustReturnLocalRecordsByFilter() {
        String startDate = "2023-01-01";
        String endDate = "2024-12-31";
        Long idUsuario = 41L;
        int actualPage = 2;

        List<RegistersResponseDTO> responseDTO =
                webTestClient.get()
                        .uri("/registros/filtros/{startDate}/{endDate}/{idUsuario}/{actualPage}", startDate, endDate, idUsuario, actualPage)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(RegistersResponseDTO.class)
                        .returnResult()
                        .getResponseBody();

        assertThat(responseDTO).isNotEmpty();
    }
}
