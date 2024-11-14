package br.fatec.bd4.web.dto;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.fatec.bd4.entity.UserSys;
import br.fatec.bd4.entity.Enum.Role;

public record UserSysResponseDTO(Long id, String name, String username, Role role) {
    public static UserSysResponseDTO toUserResponseDTO(UserSys user){
        return new UserSysResponseDTO(user.getId(), user.getName(), user.getUsername(), user.getRole());
    }

    public static UserSys toUserSys(UserSysUpdateDTO user){
        UserSys userSys = new UserSys();
        userSys.setId(user.id());
        userSys.setName(user.name());
        userSys.setRole(user.role());
        return userSys;
    }

    public static List<UserSysResponseDTO> toListUserSysResponseDTO(List<UserSys> users){
        List<UserSysResponseDTO> listUsers = users.stream().map(user -> {
            return toUserResponseDTO(user);
        }).collect(Collectors.toList());

        return listUsers;
    }
}
