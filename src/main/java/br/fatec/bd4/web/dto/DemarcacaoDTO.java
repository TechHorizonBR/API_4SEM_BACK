package br.fatec.bd4.web.dto;

import org.locationtech.jts.geom.Coordinate;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.fatec.bd4.entity.Usuario;

public record DemarcacaoDTO(
        @JsonAlias("nome") String nome,
        @JsonAlias("usuario") Usuario usuario,
        @JsonAlias("coordenadas")Coordinate coordinate
        ) {
}
