package br.fatec.bd4.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usersys")
@RequiredArgsConstructor
@Tag(name = "System User", description = "Responsible for managering systems users")
public class UserSysController {
    
    private final UserSysServiceImpl userSysServiceImpl;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserSysResponseDTO>> getAll(){
        return ResponseEntity.ok().body(UserSysResponseDTO.toListUserSysResponseDTO(userSysServiceImpl.getAll()));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<UserSysResponseDTO> create(@RequestBody UserSysCreate user){
        return ResponseEntity.status(HttpStatus.CREATED).body(UserSysResponseDTO.toUserResponseDTO(userSysServiceImpl.create(user.toUserSys(user))));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/username")
    public ResponseEntity<UserSysResponseDTO> getByUsername(@RequestParam String username){
        return ResponseEntity.ok().body(UserSysResponseDTO.toUserResponseDTO(userSysServiceImpl.findByUsername(username)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update-user")
    public ResponseEntity<UserSysResponseDTO> updateUser(@RequestParam Long id, UserSysUpdateDTO userSysUpdateDTO){
        return ResponseEntity.ok().body(
            UserSysResponseDTO.toUserResponseDTO(userSysServiceImpl.update(UserSysResponseDTO.toUserSys(userSysUpdateDTO)) 
        ));
    }

    @PatchMapping("/reset-senha")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('CLIENTE') and #id == authentication.principal.id)")
    public ResponseEntity<UserSysResponseDTO> resetSenha(@RequestParam Long id, @RequestBody UserSysResetPasswordDTO user){
        return ResponseEntity.ok().body(
            UserSysResponseDTO.toUserResponseDTO(userSysServiceImpl.resetPassword(user)
        ));
    }

}
