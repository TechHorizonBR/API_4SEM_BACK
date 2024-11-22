package br.fatec.bd4.web.dto;

import br.fatec.bd4.entity.Enum.Role;

public record UserSysUpdateDTO(Long id, String name, Role role) {
    
}
