package br.fatec.bd4.web.dto;

import java.time.LocalDateTime;


public record LocalDTO(
    LocalDateTime date, 
    String nomeDevice, 
    String nomeUsuario) {
}
