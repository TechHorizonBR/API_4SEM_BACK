package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.entity.Registro;
import br.fatec.bd4.service.DemarcacaoServiceImpl;
import br.fatec.bd4.service.interfaces.DemarcacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
