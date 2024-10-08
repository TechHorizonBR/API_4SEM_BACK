package br.fatec.bd4.web.controller;

import org.springframework.web.bind.annotation.*;
import br.fatec.bd4.entity.Ponto;
import br.fatec.bd4.service.PontoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.Duration;

@RestController
@RequestMapping("/api/pontos")
public class PontoController {

    private final PontoService pontoService;
    private Ponto pontoAnterior = null;

    public PontoController(PontoService pontoService) {
        this.pontoService = pontoService;
    }

    @Operation(summary = "Verifica o ponto atual em relação ao ponto anterior",
    description = "Retorna true se o ponto atual for o mesmo que o anterior e houver uma diferença de mais de 15 minutos.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Ponto verificado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })

    @PostMapping("/verificar")
    public boolean verificarPonto(@RequestBody Ponto pontoAtual) {
        // Se o ponto anterior não existir, atribua o ponto atual e retorne falso.
        if (pontoAnterior == null) {
            pontoAnterior = pontoAtual;
            return false; // Primeiro ponto recebido
        }

        // Comparar localização e tempo
        boolean mesmoLocal = pontoAtual.getLat() == pontoAnterior.getLat() && pontoAtual.getLon() == pontoAnterior.getLon();
        boolean maisDe15Minutos = Duration.between(pontoAnterior.getHr(), pontoAtual.getHr()).toMinutes() > 15;

        // Verifica se está parado
        boolean resultado = mesmoLocal && maisDe15Minutos && pontoAtual.isStopped();

        // Salvar o ponto atual no banco de dados
        pontoService.salvarPonto(pontoAtual);
        
        // Atualiza o ponto anterior para o próximo
        pontoAnterior = pontoAtual;

        return resultado;
    }
}
