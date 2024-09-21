package br.fatec.bd4.web.dto;

import br.fatec.bd4.entity.Usuario;

public record DadosUsuarioDTO(Long idUsuario, String nome, Long idDevice, String codigoDevice) {
    public DadosUsuarioDTO(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getDevice().getId(), usuario.getDevice().getCodigo());
    }
}
