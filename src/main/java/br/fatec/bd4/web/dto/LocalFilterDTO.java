package br.fatec.bd4.web.dto;

import java.time.LocalDateTime;

public record LocalFilterDTO(LocalDateTime StartDate, LocalDateTime endDate, String nomeDevice, String nomeUsuario) {
}
