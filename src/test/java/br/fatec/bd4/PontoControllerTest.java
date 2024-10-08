package br.fatec.bd4;

import br.fatec.bd4.service.PontoService;
import br.fatec.bd4.web.controller.PontoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(PontoController.class)
public class PontoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("unused")
    @Autowired
    private PontoService pontoService; // Você pode usar um mock aqui

    @Test
    public void testVerificarPonto() throws Exception {
        String json = "{ \"lat\": -12.5, \"lon\": 24.4, \"hr\": \"2023-12-12T11:30:00\", \"isStopped\": true }";

        mockMvc.perform(post("/api/pontos/verificar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("true")); // ou "false" dependendo do estado
    }
}
