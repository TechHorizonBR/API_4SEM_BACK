package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Device;
import br.fatec.bd4.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    // Retorna todos os dispositivos
    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.findAll();
        return ResponseEntity.ok(devices);
    }

    // Retorna um dispositivo específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        Optional<Device> device = deviceService.findById(id);
        return device.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cria um novo dispositivo
    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        Device savedDevice = deviceService.save(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDevice);
    }

    // Atualiza um dispositivo existente
    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody Device deviceDetails) {
        Device updatedDevice = deviceService.update(id, deviceDetails);
        return ResponseEntity.ok(updatedDevice);
    }

    // Exclui um dispositivo pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
