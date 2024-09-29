package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Local;
import br.fatec.bd4.service.LocalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Obter todos os locais")
    @GetMapping
    public ResponseEntity<List<Local>> getAllLocais() {
        List<Local> locais = localService.findAll();
        return ResponseEntity.ok(locais);
    }

    @Operation(summary = "Obter um local por ID")
    @Parameter(name = "id", description = "ID do local", required = true)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Local encontrado"),
        @ApiResponse(responseCode = "404", description = "Local não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Local> getLocalById(@PathVariable Long id) {
        Optional<Local> local = localService.findById(id);
        return local.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar um novo local")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Local criado"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<Local> createLocal(@RequestBody Local local) {
        Local savedLocal = localService.save(local);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLocal);
    }

    @Operation(summary = "Atualizar um local existente")
    @Parameter(name = "id", description = "ID do local", required = true)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Local atualizado"),
        @ApiResponse(responseCode = "404", description = "Local não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Local> updateLocal(@PathVariable Long id, @RequestBody Local localDetails) {
        Local updatedLocal = localService.update(id, localDetails);
        return ResponseEntity.ok(updatedLocal);
    }

    @Operation(summary = "Excluir um local pelo ID")
    @Parameter(name = "id", description = "ID do local", required = true)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Local excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Local não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocal(@PathVariable Long id) {
        localService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
