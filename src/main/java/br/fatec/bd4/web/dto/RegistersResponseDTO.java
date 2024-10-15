package br.fatec.bd4.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public record RegistersResponseDTO(
    @JsonAlias("registros") List<RegisterDTO> registers,
    @JsonAlias("paginaAtual") int currentPage,
    @JsonAlias("totalPaginas") int totalPages,
    @JsonAlias("coordBounds") MaxMinDTO maxMinCoordinates
) {
    
}
