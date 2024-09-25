package br.fatec.bd4.service;

import br.fatec.bd4.entity.Device;
import br.fatec.bd4.repository.DeviceRepository;
import br.fatec.bd4.web.dto.RegisterInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public Optional<Device> findById(Long id) {
        return deviceRepository.findById(id);
    }

    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    public void deleteById(Long id) {
        deviceRepository.deleteById(id);
    }

    public Device update(Long id, Device deviceDetails) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found with id " + id));
        device.setCodigo(deviceDetails.getCodigo());
        device.setMacAddress(deviceDetails.getMacAddress());
        device.setUsuario(deviceDetails.getUsuario());
        return deviceRepository.save(device);
    }

}
