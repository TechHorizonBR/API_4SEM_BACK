package br.fatec.bd4.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegisterInputDTO(
        @JsonAlias("createdAt") LocalDateTime createdAt,
        @JsonAlias("Latitude") Double latitude,
        @JsonAlias("Longitude") Double longitude,
        @JsonAlias("FullName") String fullName,
        @JsonAlias("LocalName") String localName
        ) {
}
