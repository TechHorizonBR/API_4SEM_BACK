package br.fatec.bd4.web.dto;

import br.fatec.bd4.entity.Local;

public record LocalDTO(Double longitude, Double altitude) {
    public LocalDTO(Local local) {
        this(local.getLongitude(), local.getAltitude());
    }

    
}
