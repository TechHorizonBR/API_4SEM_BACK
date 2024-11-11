package br.fatec.bd4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.fatec.bd4.entity.UserSys;
import br.fatec.bd4.repository.UserSysRepository;
import br.fatec.bd4.service.interfaces.UserSysService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSysServiceImpl implements UserSysService{ 
    private final UserSysRepository userSysRepository;

    @Override
    @Transactional(readOnly = false)
    public UserSys create(UserSys user) {
        if(user.getName().equals("") ||
            user.getPassword().equals("") ||
            user.getRole() == null ||
            user.getUsername().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All fields are required");

        return userSysRepository.save(user);
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

    @Override
    @Transactional(readOnly = true)
    public UserSys findByUsername(String username) {
        Optional<UserSys> user = userSysRepository.findByUsername(username);

        if(user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");

        return user.get();
    }
    
}
