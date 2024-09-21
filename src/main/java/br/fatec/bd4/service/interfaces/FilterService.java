package br.fatec.bd4.service.interfaces;

import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.web.dto.RequestUsuarios;
import br.fatec.bd4.web.dto.UsuariosResponseDTO;

import java.util.List;

public interface FilterService {
    UsuariosResponseDTO getUsersDevice(RequestUsuarios request);
}
