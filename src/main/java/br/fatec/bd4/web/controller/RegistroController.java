package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Registro;
import br.fatec.bd4.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/registros")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @GetMapping
    public List<Registro> getAllRegistros() {
        return registroService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registro> getRegistroById(@PathVariable Long id) {
        Optional<Registro> registro = registroService.findById(id);
        return registro.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Registro> createRegistro(@RequestBody Registro registro) {
        Registro savedRegistro = registroService.save(registro);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegistro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Registro> updateRegistro(@PathVariable Long id, @RequestBody Registro registroDetails) {
        Registro updatedRegistro = registroService.update(id, registroDetails);
        return ResponseEntity.ok(updatedRegistro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistro(@PathVariable Long id) {
        registroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}