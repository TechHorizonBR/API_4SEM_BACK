package br.fatec.bd4.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.fatec.bd4.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository <Device, Long> {
    Optional<Device> findByCodigo(String codigo);
}
