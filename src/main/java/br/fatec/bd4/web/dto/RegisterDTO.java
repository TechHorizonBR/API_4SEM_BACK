
package br.fatec.bd4.web.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

public record RegisterDTO(
        @JsonAlias("dataHora") LocalDateTime dataHora,
        @JsonAlias("Latitude") double latitude,
        @JsonAlias("Longitude") double longitude,
        @JsonAlias("isStopped") boolean isStopped
) { }
        