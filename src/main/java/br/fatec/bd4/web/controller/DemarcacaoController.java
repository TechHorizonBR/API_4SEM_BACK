package br.fatec.bd4.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.service.DemarcacaoServiceImpl;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        demarcacaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
