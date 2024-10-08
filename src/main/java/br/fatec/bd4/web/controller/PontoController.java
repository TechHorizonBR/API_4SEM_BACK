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
      
        if (pontoAnterior == null) {
            pontoAnterior = pontoAtual;
            return false; 
        }

       
        boolean mesmoLocal = pontoAtual.getLat() == pontoAnterior.getLat() && pontoAtual.getLon() == pontoAnterior.getLon();
        boolean maisDe15Minutos = Duration.between(pontoAnterior.getHr(), pontoAtual.getHr()).toMinutes() > 15;

        boolean resultado = mesmoLocal && maisDe15Minutos && pontoAtual.isStopped();

        pontoService.salvarPonto(pontoAtual);
        
    
        pontoAnterior = pontoAtual;

        return resultado;
    }
}
