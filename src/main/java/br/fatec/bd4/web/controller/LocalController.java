package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Local;
import br.fatec.bd4.service.LocalService;
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
@RequestMapping("/locais")
public class LocalController {

    @Autowired
    private LocalService localService;

    @Operation(
        summary = "Get all Locals.",
        description = "Endpoint responsible for retrieving a list of all locals.",
        responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Getting has been executed successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(
                        implementation = Local.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Local>> getAllLocais() {
        List<Local> locais = localService.findAll();
        return ResponseEntity.ok(locais);
    }

    @Operation(
        summary = "Get all Locals .",
        description = "Endpoint responsible for getting local information searching for id.",
        responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Getting has been executed successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(
                        implementation = Local.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Local> getLocalById(@PathVariable Long id) {
        Optional<Local> local = localService.findById(id);
        return local.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Create a new Local.",
        description = "Endpoint responsible for creating a new local",
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Local have been created..",
                        content = @Content(mediaType = "application/json", schema = @Schema(
                            implementation = Local.class))
                )
        }
    )
    @PostMapping
    public ResponseEntity<Local> createLocal(@RequestBody Local local) {
        Local savedLocal = localService.save(local);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLocal);
    }

    @Operation(
        summary = "Update an existing Local.",
        description = "Endpoint responsible for updating an existing local by its ID.",
        responses = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Device have been created..",
                    content = @Content(mediaType = "application/json", schema = @Schema(
                        implementation = Local.class))
            )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Local> updateLocal(@PathVariable Long id, @RequestBody Local localDetails) {
        Local updatedLocal = localService.update(id, localDetails);
        return ResponseEntity.ok(updatedLocal);
    }

    @Operation(
        summary = "Delete a Local.",
        description = "Endpoint responsible for deleting a Local by its ID.",
        responses = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Local deleted successfully. No content returned."
                )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocal(@PathVariable Long id) {
        localService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
