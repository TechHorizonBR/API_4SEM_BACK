package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.service.DemarcacaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demarcacoes")
@CrossOrigin("*")
public class DemarcacaoController {

    @Autowired
    private DemarcacaoServiceImpl demarcacaoService;

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
}
