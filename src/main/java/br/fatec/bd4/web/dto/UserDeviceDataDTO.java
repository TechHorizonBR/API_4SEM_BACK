package br.fatec.bd4.web.dto;

import br.fatec.bd4.entity.Usuario;

public record UserDeviceDataDTO(Long idUsuario, String nome, Long idDevice, String codigoDevice) {
    public UserDeviceDataDTO(Usuario usuario){
        this(usuario.getId(), usuario.getNome().toUpperCase(), usuario.getDevice().getId(), usuario.getDevice().getCodigo().toUpperCase());
    }
}
