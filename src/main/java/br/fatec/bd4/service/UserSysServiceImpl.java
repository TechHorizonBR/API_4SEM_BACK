package br.fatec.bd4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.fatec.bd4.entity.UserSys;
import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.entity.Enum.Role;
import br.fatec.bd4.repository.UserSysRepository;
import br.fatec.bd4.service.interfaces.UserSysService;
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

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userSysRepository.save(user);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Username '%s' já cadastrado", user.getUsername()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserSys getById(Long id) {
        Optional<UserSys> userFound = userSysRepository.findById(id);

        if(userFound.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
        
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
    public UserSys resetPassword(String username, String password) {
        UserSys userFound = findByUsername(username);

        userFound.setPassword(password);
        return userSysRepository.save(userFound);
    }

    @Override
    public UserSys update(UserSys user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");

        return user.get();
    }
    
}
