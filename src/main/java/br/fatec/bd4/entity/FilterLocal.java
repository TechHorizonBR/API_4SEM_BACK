package br.fatec.bd4.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FilterLocal {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String nomeDevice;
    private String nomeUsuario;
}
