package br.fatec.bd4.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.fatec.bd4.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository <Device, Long> {
    List<Device> findByCodigo(String codigo);
    System.out.println("test")
}
