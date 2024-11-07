package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.entity.Device;
import br.fatec.bd4.service.DemarcacaoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.service.DemarcacaoServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demarcacoes")
@CrossOrigin("*")
public class DemarcacaoController {

    @Autowired
    private DemarcacaoServiceImpl demarcacaoService;


    //Cria uma nova demarcação


    @Operation(
            summary = "Create a new Demarcation.",
            description = "Endpoint responsible for creating a new Demarcation.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Demarcation have been created..",
                            content = @Content(mediaType = "application/json", schema = @Schema(
                                implementation = Demarcacao.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<List<Demarcacao>> createDemarcacoes(@RequestBody Map<String, Object> requestData) {
        try {
            if (!requestData.containsKey("nome") || !requestData.containsKey("usuarioId") || !requestData.containsKey("coordinates")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            String nome = (String) requestData.get("nome");
            Long usuarioId = ((Number) requestData.get("usuarioId")).longValue();
            List<List<List<Double>>> polygonsCoordinates = (List<List<List<Double>>>) requestData.get("coordinates");

            if (polygonsCoordinates.isEmpty() || polygonsCoordinates.stream().anyMatch(coords -> coords.size() < 4)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            List<Demarcacao> demarcacoes = demarcacaoService.saveDemarcacoes(nome, usuarioId, polygonsCoordinates);
            return ResponseEntity.status(HttpStatus.CREATED).body(demarcacoes);

        } catch (ClassCastException | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    //Deletar demarcações
    @Operation(
        summary = "Delete a Demarcation.",
        description = "Endpoint responsible for deleting a demarcation by its ID.",
        responses = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Demarcation deleted successfully. No content returned."
                )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        demarcacaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    //Atualizar demarcações
    
    @Operation(
        summary = "Update an existing Demarcation.",
        description = "Endpoint responsible for updating an existing deamrcation  by its ID.",
        responses = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Deamrcation have been updated..",
                    content = @Content(mediaType = "application/json", schema = @Schema(
                        implementation = Demarcacao.class))
            )
            }
        )

    @PatchMapping("/{id}")
    public ResponseEntity<Demarcacao> updateDemarcacao(@PathVariable Long id, @RequestBody Map<String, Object> requestData) {
    try {
        if (!requestData.containsKey("nome") || !requestData.containsKey("usuarioId") || !requestData.containsKey("coordinates")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String nome = (String) requestData.get("nome");
        Long usuarioId = ((Number) requestData.get("usuarioId")).longValue();
        List<List<List<Double>>> polygonsCoordinates = (List<List<List<Double>>>) requestData.get("coordinates");

        if (polygonsCoordinates.isEmpty() || polygonsCoordinates.stream().anyMatch(coords -> coords.size() < 4)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Demarcacao updatedDemarcacao = demarcacaoService.updateDemarcacao(id, nome, usuarioId, polygonsCoordinates);
        
        if (updatedDemarcacao == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(updatedDemarcacao);
    } catch (ClassCastException | NullPointerException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
}
