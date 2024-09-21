package br.fatec.bd4.service;

import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.repository.UsuarioRepository;
import br.fatec.bd4.service.interfaces.FilterService;
import br.fatec.bd4.web.dto.DadosUsuarioDTO;
import br.fatec.bd4.web.dto.RequestUsuarios;
import br.fatec.bd4.web.dto.UsuariosResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FilterServiceImpl implements FilterService{
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UsuariosResponseDTO getUsersDevice(RequestUsuarios requestUsuarios) throws NoSuchElementException {
        PageRequest page = PageRequest.of(requestUsuarios.page(), 10);

        Page<Usuario> usuariosPage = usuarioRepository.findAllUsuarios(page);

        Page<DadosUsuarioDTO> dadosUsuariosPage = usuariosPage.map(DadosUsuarioDTO::new);

        int pageActual = dadosUsuariosPage.getNumber();
        int totalPages = dadosUsuariosPage.getTotalPages();

        List<DadosUsuarioDTO> usersResponse = dadosUsuariosPage.getContent();

        return new UsuariosResponseDTO(usersResponse, pageActual, totalPages);
    }
}
