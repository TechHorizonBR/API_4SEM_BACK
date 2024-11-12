package br.fatec.bd4.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.fatec.bd4.jwt.JwtToken;
import br.fatec.bd4.jwt.JwtUserDetailsService;
import br.fatec.bd4.web.dto.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticacaoController {
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody UserLoginDTO userLoginDTO){
        try{
            System.out.println(userLoginDTO.username()+userLoginDTO.password());
            UsernamePasswordAuthenticationToken autgAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(userLoginDTO.username(), userLoginDTO.password());

            authenticationManager.authenticate(autgAuthenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(userLoginDTO.username());

            return ResponseEntity.ok(token);
        }catch(AuthenticationException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad credentials.");
        }
    }
}
