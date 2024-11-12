package br.fatec.bd4.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.fatec.bd4.entity.UserSys;
import br.fatec.bd4.service.UserSysServiceImpl;
import br.fatec.bd4.web.dto.UserSysCreate;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usersys")
@RequiredArgsConstructor
public class UserSysController {
    
    private final UserSysServiceImpl userSysServiceImpl;

    @GetMapping
    public ResponseEntity<List<UserSys>> getAll(){
        return ResponseEntity.ok().body(userSysServiceImpl.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<UserSys> create(@RequestBody UserSysCreate user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userSysServiceImpl.create(user.toUserSys(user)));
    }

    @GetMapping("/username")
    public ResponseEntity<UserSys> getByUsername(@RequestParam String username){
        return ResponseEntity.ok().body(userSysServiceImpl.findByUsername(username));
    }
}
