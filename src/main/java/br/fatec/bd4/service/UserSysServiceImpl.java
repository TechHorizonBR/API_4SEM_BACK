package br.fatec.bd4.service;


import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.fatec.bd4.entity.UserSys;
import br.fatec.bd4.entity.Enum.Role;
import br.fatec.bd4.repository.UserSysRepository;
import br.fatec.bd4.service.interfaces.UserSysService;
import br.fatec.bd4.web.dto.UserSysResetPasswordDTO;
import br.fatec.bd4.web.exception.EntityNotFoundException;
import br.fatec.bd4.web.exception.InvalidPasswords;
import br.fatec.bd4.web.exception.UserAldearyExist;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSysServiceImpl implements UserSysService{ 
    private final UserSysRepository userSysRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = false)
    public UserSys create(UserSys user) {
        if(user.getName().trim().isBlank() ||
            user.getPassword().trim().isBlank() ||
            user.getRole() == null ||
            user.getUsername().trim().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All fields are required");
        Optional<UserSys> userByUserName = userSysRepository.findByUsername(user.getUsername());

        if(userByUserName.isPresent()){
            throw new UserAldearyExist("Username já cadastrado");
        }
    
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userSysRepository.save(user);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UserAldearyExist("Username já cadastrado");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserSys getById(Long id) {
        if(id == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "USUÁRIO NÃO PODE SER NULO");
        Optional<UserSys> userFound = userSysRepository.findById(id);

        if(userFound.isEmpty())
            throw new EntityNotFoundException("Usuário não existe.");
        
        return userFound.get();
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(Long id) {
        UserSys user = getById(id);
        userSysRepository.deleteById(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserSys> getAll() {
        return userSysRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public UserSys resetPassword(UserSysResetPasswordDTO userSys) {
        UserSys userFound = getById(userSys.id());

        if(!userSys.password().equals(userSys.passwordConfirmation())){
            throw new InvalidPasswords("As senhas não são iguais.");
        }
        userFound.setPassword(passwordEncoder.encode(userSys.passwordConfirmation()));
        return userSysRepository.save(userFound);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteByUsername(String username) {
        UserSys user = userSysRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));
        userSysRepository.deleteById(user.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public UserSys update(UserSys user) {
        System.out.println(user.getId());
        UserSys userFound = getById(user.getId());
        userFound.setName(user.getName());
        userFound.setRole(user.getRole());
        return userSysRepository.save(userFound);
    }

    @Transactional(readOnly = true)
    public Role buscarRolePorUsername(String username) {
        return userSysRepository.findRoleByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserSys findByUsername(String username) {
        Optional<UserSys> user = userSysRepository.findByUsername(username);

        if(user.isEmpty())
            throw new EntityNotFoundException("Usuário não existe.");

        return user.get();
    }
    
}
