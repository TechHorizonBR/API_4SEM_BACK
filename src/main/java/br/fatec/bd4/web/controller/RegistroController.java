package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Registro;
import br.fatec.bd4.service.RegistroService;
import br.fatec.bd4.web.dto.RegisterDTO;
import br.fatec.bd4.web.dto.RegisterInputDTO;
import br.fatec.bd4.web.dto.RegistersResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/registros")
@CrossOrigin("*")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @Operation(
        summary = "Get all Registers.",
        description = "Endpoint responsible for retrieving a list of all registers.",
        responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Getting has been executed successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(
                        implementation = Registro.class))
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Registro> getAllRegistros() {
        return registroService.findAll();
    }

    @Operation(
            summary = "Get the register.",
            description = "Endpoint responsible for getting register information searching for id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Getting has been executed successfully.",
                            content = @Content(mediaType = "application/json", schema = @Schema(
                                implementation = Registro.class))
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Registro> getRegistroById(@PathVariable Long id) {
        Optional<Registro> registro = registroService.findById(id);
        return registro.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new Register.",
            description = "Endpoint responsible for creating a new device, receiving a list of UserDeviceDataDTO to input the data.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Register have been created..",
                            content = @Content(mediaType = "application/json", schema = @Schema(
                                implementation = Registro.class))
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Registro> createRegistro(@RequestBody Registro registro) {
        Registro savedRegistro = registroService.save(registro);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegistro);
    }

    @Operation(
        summary = "Delete a Register.",
        description = "Endpoint responsible for deleting a registe by its ID.",
        responses = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Register deleted successfully. No content returned."
                )
        }
    )
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/input-registers")
    public ResponseEntity<Void> inputRegisters(@RequestBody List<RegisterInputDTO> registers){
        registroService.inputRegisters(registers);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/input-registers-upload-file")
    public ResponseEntity<Void> inputRegistersByUpload(@RequestParam("file") MultipartFile file){
        registroService.inputRegistersByUploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(
        summary = "Find local records by filters.",
        description = "Endpoint responsible for getting a registered location filtered by date range and user ID.",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved filtered records.",
                        content = @Content(mediaType = "application/json", schema = @Schema(
                        implementation = RegisterDTO.class)
                            )
                )
        }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @GetMapping("/filtros/{startDate}/{endDate}/{idUsuario}/{actualPage}")
    public ResponseEntity<RegistersResponseDTO> findLocalByFilters(
                                                                    @PathVariable String startDate,
                                                                    @PathVariable String endDate,
                                                                    @PathVariable Long idUsuario,
                                                                    @PathVariable int actualPage
                                                                ){
        RegistersResponseDTO response = registroService.findLocalByFilters(startDate, endDate, idUsuario, actualPage);
        return ResponseEntity.ok(response);
    }
}

