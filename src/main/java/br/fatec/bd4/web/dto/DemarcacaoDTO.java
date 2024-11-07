package br.fatec.bd4.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;



public record DemarcacaoDTO(
        @JsonAlias("nome") String nome,
        @JsonAlias("coordenadas")List<List<Double>> coordinate
        ) {
}
