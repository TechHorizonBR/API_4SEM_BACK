package br.fatec.bd4.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fatec.bd4.entity.UserSys;
import br.fatec.bd4.service.UserSysServiceImpl;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    public ResponseEntity<UserSys> create(@RequestBody UserSys user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userSysServiceImpl.create(user));
    }
}
