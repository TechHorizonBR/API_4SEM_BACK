
package br.fatec.bd4.web.dto;

import java.time.LocalDateTime;

import br.fatec.bd4.entity.Registro;
import com.fasterxml.jackson.annotation.JsonAlias;

public record RegisterDTO(
        @JsonAlias("dataHora") LocalDateTime dataHora,
        @JsonAlias("Latitude") double latitude,
        @JsonAlias("Longitude") double longitude,
        @JsonAlias("isStopped") boolean isStopped
) {
    public static RegisterDTO fromEntity(Registro registro) {
        return new RegisterDTO(
                registro.getDataHora(),
                registro.getLocal().getLatitude(),
                registro.getLocal().getLongitude(),
                false
        );
    }

}
        