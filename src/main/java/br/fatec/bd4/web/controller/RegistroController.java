package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Registro;
import br.fatec.bd4.service.RegistroService;
import br.fatec.bd4.web.dto.RegisterDTO;
import br.fatec.bd4.web.dto.RegisterInputDTO;
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


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistro(@PathVariable Long id) {
        registroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/input-registers")
    public ResponseEntity<Void> inputRegisters(@RequestBody List<RegisterInputDTO> registers){
        registroService.inputRegisters(registers);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/filtros/{startDate}/{endDate}/{idUsuario}")
    public ResponseEntity<List<RegisterDTO>> findLocalByFilters(
        @PathVariable() String startDate,
        @PathVariable() String endDate,
        @PathVariable() Long idUsuario
    ) {

        List<RegisterDTO> registros = registroService.findLocalByFilters(startDate, endDate, idUsuario);
        return ResponseEntity.ok(registros);
    }
}
