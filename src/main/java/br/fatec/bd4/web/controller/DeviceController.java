package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Device;
import br.fatec.bd4.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Operation(
        summary = "Get all Devices.",
        description = "Endpoint responsible for retrieving a list of all devices.",
        responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Getting has been executed successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(
                        implementation = Device.class))
                    )
            }
        )        
    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.findAll();
        return ResponseEntity.ok(devices);
    }

    @Operation(
            summary = "Get the Device.",
            description = "Endpoint responsible for getting device information searching for id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Getting has been executed successfully.",
                            content = @Content(mediaType = "application/json", schema = @Schema(
                                implementation = Device.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        Optional<Device> device = deviceService.findById(id);
        return device.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new Device.",
            description = "Endpoint responsible for creating a new device, receiving a list of UserDeviceDataDTO to input the data.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Device have been created..",
                            content = @Content(mediaType = "application/json", schema = @Schema(
                                implementation = Device.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        Device savedDevice = deviceService.save(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDevice);
    }

    @Operation(
        summary = "Update an existing Device.",
        description = "Endpoint responsible for updating an existing device by its ID.",
        responses = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Device have been created..",
                    content = @Content(mediaType = "application/json", schema = @Schema(
                        implementation = Device.class))
            )
            }
        )
    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody Device deviceDetails) {
        Device updatedDevice = deviceService.update(id, deviceDetails);
        return ResponseEntity.ok(updatedDevice);
    }

    @Operation(
        summary = "Delete a Device.",
        description = "Endpoint responsible for deleting a device by its ID.",
        responses = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Device deleted successfully. No content returned."
                )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
