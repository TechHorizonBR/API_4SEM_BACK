package br.fatec.bd4.service;

import br.fatec.bd4.entity.Device;
import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.repository.UsuarioRepository;
import br.fatec.bd4.web.dto.UserInputDTO;
import br.fatec.bd4.web.dto.UsuarioDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private DeviceService deviceService;
    
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doens't exist");
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.get().getId(), usuario.get().getNome());

        return usuarioDTO;
    }

    @Transactional(readOnly = true)
    public Usuario findByNameAndCreate(String nome){
        Optional<Usuario> usuario = usuarioRepository.findByNome(nome.toUpperCase());

        if (usuario.isPresent()){
            return usuario.get();
        }else{
            return usuarioRepository.save(new Usuario(nome.toUpperCase()));
        }
    }
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario update(Long id, Usuario usuarioDetails) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id " + id));
        usuario.setNome(usuarioDetails.getNome());
        usuario.setDevice(usuarioDetails.getDevice());
        return usuarioRepository.save(usuario);
    }
    @Transactional
    public void inputUsers(List<UserInputDTO> users) {
        for(UserInputDTO user : users){
            Optional<Usuario> usuarioFound = usuarioRepository.findByNome(user.fullName().toUpperCase());

            if(usuarioFound.isEmpty()){
                Usuario userCadastrado = usuarioRepository.save(new Usuario(user.fullName().toUpperCase()));
                deviceService.save(new Device(user.codigoDevice(), user.macAddress(),userCadastrado));
            }
        }
    }
}
