package br.fatec.bd4.web.controller;

import br.fatec.bd4.service.FilterServiceImpl;
import br.fatec.bd4.web.dto.RequestUsuarios;
import br.fatec.bd4.web.dto.UsuariosResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/filters")
@CrossOrigin(value = "*")
@AllArgsConstructor
@Tag(name = "Filters", description = "Endpoints responsible for getting values from database")
public class FilterController {
    private final FilterServiceImpl filterService;

    @GetMapping("/user-device")
    @Operation(
            summary = "Get Users and Devices",
            description = "Endpoint responsible for getting users and devices",
            responses = {
                    @ApiResponse(responseCode = "200",
                                description = "Getting has been executed successfully.",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuariosResponseDTO.class)))
            }
    )
    public ResponseEntity<UsuariosResponseDTO> getUsersDevices(@RequestBody RequestUsuarios request) {
        return ResponseEntity.status(HttpStatus.OK).body(filterService.getUsersDevice(request));
    }


}
