package br.fatec.bd4.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.fatec.bd4.jwt.JwtToken;
import br.fatec.bd4.jwt.JwtUserDetailsService;
import br.fatec.bd4.web.dto.UserLoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import br.fatec.bd4.web.exception.ErrorMessage;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Recurso para proceder com a autenticação na API.")
@CrossOrigin("*")
public class AutenticacaoController {
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "Autenticar na API", description = "Recurso de autenticação na API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso e retorno de um Bearer token",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtToken.class))),
                    @ApiResponse(responseCode = "400", description = "Credenciais invalidas",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))    }
    )
    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request){
        try{
            System.out.println(userLoginDTO.username()+userLoginDTO.password());
            UsernamePasswordAuthenticationToken autgAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(userLoginDTO.username(), userLoginDTO.password());

            authenticationManager.authenticate(autgAuthenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(userLoginDTO.username());

            return ResponseEntity.ok(token);
        }catch(AuthenticationException ex){
            log.error("Credências invalidas");
        }
        return ResponseEntity
            .badRequest()
            .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
    }
}
