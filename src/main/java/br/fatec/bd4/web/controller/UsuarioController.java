package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.ResponseSchema;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Retorna todos os usuários")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados", 
                 content = @Content(mediaType = "application/json", 
                                    schema = @Schema(implementation = Usuario.class)))
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Retorna um usuário específico pelo ID")
    @Parameter(name = "id", description = "ID do usuário", required = true)
    @ApiResponse(responseCode = "200", description = "Usuário encontrado", 
                 content = @Content(mediaType = "application/json", 
                                    schema = @Schema(implementation = Usuario.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria um novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", 
                 content = @Content(mediaType = "application/json", 
                                    schema = @Schema(implementation = Usuario.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    @Operation(summary = "Atualiza um usuário existente")
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

    @Operation(summary = "Exclui um usuário pelo ID")
    @Parameter(name = "id", description = "ID do usuário a ser excluído", required = true)
    @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
