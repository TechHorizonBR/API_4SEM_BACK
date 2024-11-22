package br.fatec.bd4.service.interfaces;

import java.util.List;

import br.fatec.bd4.entity.UserSys;
import br.fatec.bd4.web.dto.UserSysResetPasswordDTO;

public interface UserSysService {
    UserSys create(UserSys user);
    UserSys getById(Long id);
    void deleteById(Long id);
    List<UserSys> getAll();
    UserSys resetPassword(UserSysResetPasswordDTO user);
    UserSys update(UserSys user);
    UserSys findByUsername(String username);
}
