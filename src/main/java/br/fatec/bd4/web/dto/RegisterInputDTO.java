package br.fatec.bd4.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegisterInputDTO(
        @JsonAlias("CreatedAt") String createdAt,
        @JsonAlias("Latitude") Double latitude,
        @JsonAlias("Longitude") Double longitude,
        @JsonAlias("FullName") String fullName,
        @JsonAlias("LocalName") String localName
        ) {
}
