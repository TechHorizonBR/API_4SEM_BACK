package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Registro;
import br.fatec.bd4.service.RegistroService;
import br.fatec.bd4.web.dto.RegisterInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Endpoint responsible for putting new registers into application.",
            description = "Endpoint responsible for putting new user into application, receiving a list of RegisterInputDTO to input the data.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Registers have been created.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    )
            }
    )
    @PostMapping("/input-registers")
    public ResponseEntity<Void> inputRegisters(@RequestBody List<RegisterInputDTO> registers){
        registroService.inputRegisters(registers);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
