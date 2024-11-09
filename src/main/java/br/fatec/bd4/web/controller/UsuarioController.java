package br.fatec.bd4.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.service.UsuarioService;
import br.fatec.bd4.web.dto.UserInputDTO;
import br.fatec.bd4.web.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Retorna todos os usuários
    @Operation(summary = "Return all users")
    @ApiResponse(responseCode = "200", description = "Users Found", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = Usuario.class)))
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // Retorna um usuário específico pelo ID
    @Operation(summary = "Return an especific user by ID")
    @Parameter(name = "id", description = "ID do usuário", required = true)
    @ApiResponse(responseCode = "200", description = "Usuário encontrado", 
    content = @Content(mediaType = "application/json", 
                                    schema = @Schema(implementation = Usuario.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    // Cria um novo usuário
    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", 
    content = @Content(mediaType = "application/json", 
                                    schema = @Schema(implementation = Usuario.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    // Atualiza um usuário existente
    @Operation(summary = "Update an existent user")
    @Parameter(name = "id", description = "ID do usuário a ser atualizado", required = true)
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", 
    content = @Content(mediaType = "application/json", 
                                    schema = @Schema(implementation = Usuario.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        Usuario updatedUsuario = usuarioService.update(id, usuarioDetails);
        return ResponseEntity.ok(updatedUsuario);
    }

    // Exclui um usuário pelo ID
    @Operation(summary = "Delete an user by id")
    @Parameter(name = "id", description = "ID do usuário a ser excluído", required = true)
    @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Endpoint responsible for putting new user into application.",
            description = "Endpoint responsible for putting new user into application, receiving a list of UserInputDTO to input the data.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Users have been created.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    )
            }
    )
    @PostMapping("/input-users")
    public ResponseEntity<Void> inputUsers(@RequestBody List<UserInputDTO> users){
        usuarioService.inputUsers(users);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
