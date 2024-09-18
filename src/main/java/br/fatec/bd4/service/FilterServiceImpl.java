package br.fatec.bd4.service;

import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FilterServiceImpl {
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Usuario> getUsersDevice(){
        return usuarioRepository.findAll();
    }


}
