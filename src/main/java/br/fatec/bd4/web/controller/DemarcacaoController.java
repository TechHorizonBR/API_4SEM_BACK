package br.fatec.bd4.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.service.DemarcacaoServiceImpl;
import br.fatec.bd4.service.UsuarioService;
import jakarta.persistence.Id;
import lombok.Getter;
import java.util.Optional;

@RestController
@RequestMapping("/demarcacoes")
@CrossOrigin("*")
public class DemarcacaoController {

    @Autowired
    private DemarcacaoServiceImpl demarcacaoService;

    @PostMapping("/new")
    public ResponseEntity<Demarcacao> createDemarcacao() {
        Demarcacao savedDemarcacao = demarcacaoService.saveDemarcacao();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDemarcacao);
    }

    // //Metodo de busca de demarcação por usuario
    // @GetMapping("/user/{id}")
    // public ResponseEntity<List<Demarcacao>> getDemarcacaoByUsuario(@PathVariable Long idUsuario) {
    //     // Busca as demarcações associadas ao idUsuario
    //     List<Demarcacao> demarcacoes = demarcacaoService.getDemarcacaoByUsuarioId(idUsuario);
    
    //     // Se não houver demarcações, retorna 404 Not Found
    //     if (demarcacoes.isEmpty()) {
    //         return ResponseEntity.notFound().build();
    //     } else {
    //         // Retorna 200 OK com a lista de demarcações
    //         return ResponseEntity.ok(demarcacoes);
    //     }
    // }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Demarcacao>> getDemarcacaoByUsuario(@PathVariable Long id) {
        // Busca as demarcações associadas ao idUsuario (agora id)
        List<Demarcacao> demarcacoes = demarcacaoService.getDemarcacaoByUsuarioId(id);
        
        // Se não houver demarcações, retorna 404 Not Found
        if (demarcacoes.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            // Retorna 200 OK com a lista de demarcações
            return ResponseEntity.ok(demarcacoes);
        }
    }
    
}
    