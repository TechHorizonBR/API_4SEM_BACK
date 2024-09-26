package br.fatec.bd4.service.interfaces;

import br.fatec.bd4.web.dto.UserDeviceDataDTO;

import java.util.List;

public interface FilterService {
    List<UserDeviceDataDTO> getUsersDevice();
}
