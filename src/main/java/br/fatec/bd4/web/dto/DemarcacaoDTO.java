package br.fatec.bd4.web.dto;

import br.fatec.bd4.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.locationtech.jts.geom.Coordinate;

import java.util.List;

public record DemarcacaoDTO(
        @JsonAlias("nome") String nome,
        @JsonAlias("usuario") Usuario usuario,
        @JsonAlias("coordenadas")Coordinate coordinate
        ) {
}
