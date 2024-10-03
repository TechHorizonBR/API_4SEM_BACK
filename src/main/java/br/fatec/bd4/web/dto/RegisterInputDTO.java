package br.fatec.bd4.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record RegisterInputDTO(
        @JsonAlias("CreatedAt") String createdAt,
        @JsonAlias("Latitude") Double latitude,
        @JsonAlias("Longitude") Double longitude,
        @JsonAlias("FullName") String fullName,
        @JsonAlias("LocalName") String localName
        ) {
}
