package br.fatec.bd4.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.fatec.bd4.service.UserSysServiceImpl;
import br.fatec.bd4.web.dto.UserSysCreate;
import br.fatec.bd4.web.dto.UserSysResetPasswordDTO;
import br.fatec.bd4.web.dto.UserSysResponseDTO;
import br.fatec.bd4.web.dto.UserSysUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usersys")
@RequiredArgsConstructor
@Tag(name = "System User", description = "Responsible for managing systems users")
public class UserSysController {
    
    private final UserSysServiceImpl userSysServiceImpl;

     @Operation(
        summary = "Get all System Users.",
        description = "Endpoint responsible for getting a list of all system users. Just ADMIN has access to this endpoint.",
        responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Getting has been executed successfully.",
                    content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserSysResponseDTO.class)))) 
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserSysResponseDTO>> getAll(){
        return ResponseEntity.ok().body(UserSysResponseDTO.toListUserSysResponseDTO(userSysServiceImpl.getAll()));
    }

    @Operation(
        summary = "Create a new System User.",
        description = "Endpoint responsible for creating a new system user. Just ADMIN has access to this endpoint.",
        responses = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User has been created successfully.",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserSysResponseDTO.class))) 
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<UserSysResponseDTO> create(@RequestBody UserSysCreate user){
        return ResponseEntity.status(HttpStatus.CREATED).body(UserSysResponseDTO.toUserResponseDTO(userSysServiceImpl.create(user.toUserSys(user))));
    }

    @Operation(
        summary = "Get System User by username.",
        description = "Endpoint responsible for getting an user by username. Just ADMIN has access to this endpoint.",
        responses = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User has been created successfully.",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserSysResponseDTO.class))) 
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @GetMapping("/username")
    public ResponseEntity<UserSysResponseDTO> getByUsername(@RequestParam String username){
        return ResponseEntity.ok().body(UserSysResponseDTO.toUserResponseDTO(userSysServiceImpl.findByUsername(username)));
    }


    @Operation(
        summary = "Delete System user.",
        description = "Endpoint responsible for deleting a User. Just Admin has access to this endpoint.",
        responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User has been deleted.",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserSysResponseDTO.class))) 
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userSysServiceImpl.deleteByUsername(username);
        return ResponseEntity.noContent().build();
}

    @Operation(
        summary = "Update System User.",
        description = "Endpoint responsible for updating a system user. Just ADMIN has access to this endpoint.",
        responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User has been updated successfully.",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserSysResponseDTO.class))) 
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update-user")
    public ResponseEntity<UserSysResponseDTO> updateUser(@RequestParam Long id, UserSysUpdateDTO userSysUpdateDTO){
        return ResponseEntity.ok().body(
            UserSysResponseDTO.toUserResponseDTO(userSysServiceImpl.update(UserSysResponseDTO.toUserSys(userSysUpdateDTO)) 
        ));
    }

    @Operation(
        summary = "Reset password.",
        description = "Endpoint responsible for reseting password. ADMIN and CLIENTE have access to this endpoint.",
        responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Password has been reseted successfully.",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserSysResponseDTO.class))) 
            }
    )
    @PatchMapping("/reset-senha")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('CLIENTE') and #id == authentication.principal.id)")
    public ResponseEntity<UserSysResponseDTO> resetSenha(@RequestParam Long id, @RequestBody UserSysResetPasswordDTO user){
        return ResponseEntity.ok().body(
            UserSysResponseDTO.toUserResponseDTO(userSysServiceImpl.resetPassword(user)
        ));
    }

}
