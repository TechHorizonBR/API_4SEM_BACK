package br.fatec.bd4.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.fatec.bd4.entity.UserSys;
import br.fatec.bd4.entity.Enum.Role;

public record UserSysCreate(
    @JsonAlias("nome")
    String name, 
    @JsonAlias("username")
    String username, 
    @JsonAlias("senha")
    String password, 
    @JsonAlias("autorizacao")
    Role role) {

    public UserSys toUserSys(UserSysCreate user){
        UserSys userSys = new UserSys();
        System.out.println(user.name);
        userSys.setName(user.name);
        userSys.setPassword(user.password);
        userSys.setRole(user.role);
        userSys.setUsername(user.username);

        return userSys;
    };
}
