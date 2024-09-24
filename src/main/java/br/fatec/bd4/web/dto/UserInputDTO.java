package br.fatec.bd4.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record UserInputDTO(
        @JsonAlias("CodeDevice") String codigoDevice,
        @JsonAlias("MacAddress") String macAddress,
        @JsonAlias("Fullname") String fullName
) {
}
