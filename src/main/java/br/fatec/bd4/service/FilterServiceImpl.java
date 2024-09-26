package br.fatec.bd4.service;

import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.repository.UsuarioRepository;
import br.fatec.bd4.service.interfaces.FilterService;
import br.fatec.bd4.web.dto.UserDeviceDataDTO;
import lombok.AllArgsConstructor;
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
    public List<UserDeviceDataDTO> getUsersDevice() {
        List<Usuario> users = usuarioRepository.findAllUsuarios();

        List<UserDeviceDataDTO> usersData = users.stream()
                .map(user -> new UserDeviceDataDTO(user))
                .collect(Collectors.toList());

        return usersData;
    }
}
