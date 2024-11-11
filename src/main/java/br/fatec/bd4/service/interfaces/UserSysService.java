package br.fatec.bd4.service.interfaces;

import java.util.List;

import br.fatec.bd4.entity.UserSys;

public interface UserSysService {
    UserSys create(UserSys user);
    UserSys getById(Long id);
    void deleteById(Long id);
    List<UserSys> getAll();
    UserSys resetPassword(String username, String password);
    UserSys update(UserSys user);
    UserSys findByUsername(String username);
}
