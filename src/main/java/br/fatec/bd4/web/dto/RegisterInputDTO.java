package br.fatec.bd4.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegisterInputDTO(
        @JsonAlias("createdAt") LocalDateTime createdAt,
        @JsonAlias("Latitude") Double latitude,
        @JsonAlias("Altitude") Double altitude,
        @JsonAlias("FullName") String fullName
        ) {
}
