package br.fatec.bd4.web.dto;

import br.fatec.bd4.entity.UserSys;

public record UserSysCreate(String name, String username, String password, int role) {

    public UserSys toUserSys(){
        return new UserSys();
    };
}
