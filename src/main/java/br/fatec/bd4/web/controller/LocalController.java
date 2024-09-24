package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Local;
import br.fatec.bd4.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locais")
public class LocalController {

    @Autowired
    private LocalService localService;

    // Retorna todos os locais
    @GetMapping
    public ResponseEntity<List<Local>> getAllLocais() {
        List<Local> locais = localService.findAll();
        return ResponseEntity.ok(locais);
    }

    // Retorna um local específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Local> getLocalById(@PathVariable Long id) {
        Optional<Local> local = localService.findById(id);
        return local.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cria um novo local
    @PostMapping
    public ResponseEntity<Local> createLocal(@RequestBody Local local) {
        Local savedLocal = localService.save(local);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLocal);
    }

    // Atualiza um local existente
    @PutMapping("/{id}")
    public ResponseEntity<Local> updateLocal(@PathVariable Long id, @RequestBody Local localDetails) {
        Local updatedLocal = localService.update(id, localDetails);
        return ResponseEntity.ok(updatedLocal);
    }

    // Exclui um local pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocal(@PathVariable Long id) {
        localService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
