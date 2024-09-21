package br.fatec.bd4.web.dto;

import java.util.List;

public record UsuariosResponseDTO(List<DadosUsuarioDTO> usuarios, int currentPage, int totalPages) {
}
