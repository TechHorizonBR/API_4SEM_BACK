package br.fatec.bd4.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.service.DemarcacaoServiceImpl;
import br.fatec.bd4.web.dto.DemarcacaoDTO;

@RestController
@RequestMapping("/demarcacoes")
@CrossOrigin("*")
public class DemarcacaoController {

    @Autowired
    private DemarcacaoServiceImpl demarcacaoService;

    @PostMapping
    public ResponseEntity<List<Demarcacao>> createDemarcacoes(@RequestBody Map<String, Object> requestData) {
        try {
            if (!requestData.containsKey("nome") || !requestData.containsKey("usuarioId")
                    || !requestData.containsKey("coordinates")) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        demarcacaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Demarcacao> updateDemarcacao(@PathVariable Long id,
            @RequestBody Map<String, Object> requestData) {
        try {
            if (!requestData.containsKey("nome") || !requestData.containsKey("usuarioId")
                    || !requestData.containsKey("coordinates")) {
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

    @GetMapping("/user/{id}")
    public ResponseEntity<List<DemarcacaoDTO>> getDemarcacaoByUsuario(@PathVariable Long id) {
        // Busca as demarcações associadas ao idUsuario (agora id)
        List<DemarcacaoDTO> demarcacoes = demarcacaoService.getDemarcacaoByUsuarioId(id);

        // Se não houver demarcações, retorna 404 Not Found
        if (demarcacoes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());  // Retorna 200 com lista vazia
        } else {
            // Retorna 200 OK com a lista de demarcações
            return ResponseEntity.ok(demarcacoes);
        }
}
}