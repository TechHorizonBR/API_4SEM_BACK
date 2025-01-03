package br.fatec.bd4.service;

import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.repository.UsuarioRepository;
import br.fatec.bd4.service.interfaces.FilterService;
import br.fatec.bd4.web.dto.UserDeviceDataDTO;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilterServiceImpl implements FilterService{
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    @Cacheable("usuariosDevices")
    public List<UserDeviceDataDTO> getUsersDevice() {
        List<Usuario> users = usuarioRepository.findAllUsuarios();
        
        List<UserDeviceDataDTO> usersData = users.stream()
                .filter(user -> user != null && user.getDevice() != null && user.getNome() != null) // Filtra os nulos
                .map(UserDeviceDataDTO::new) // Mapeia para o DTO apenas os filtrados
                .collect(Collectors.toList());

        return usersData;
    }
}
