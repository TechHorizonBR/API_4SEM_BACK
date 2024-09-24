package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.service.UsuarioService;
import br.fatec.bd4.web.dto.UserInputDTO;
import org.apache.coyote.Response;
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

    // Retorna todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // Retorna um usuário específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cria um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    // Atualiza um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        Usuario updatedUsuario = usuarioService.update(id, usuarioDetails);
        return ResponseEntity.ok(updatedUsuario);
    }

    // Exclui um usuário pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/input-users")
    public ResponseEntity<Void> inputUsers(@RequestBody List<UserInputDTO> users){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
