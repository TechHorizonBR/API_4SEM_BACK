package br.fatec.bd4.repository;

import br.fatec.bd4.entity.Local;

import java.util.List;
import java.time.LocalDateTime;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.fatec.bd4.web.dto.LocalDTO;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

    @Query(
        value = "SELECT l.longitude, l.latitude, r.data_hora " +
                "FROM Local l " +
                "INNER JOIN Registro r ON r.local_id = l.id " +
                "INNER JOIN Device d ON r.device_id = d.id " +
                "INNER JOIN Usuario u ON d.usuario_id = u.id " +
                "WHERE r.data_hora BETWEEN :startDate AND :endDate " +
                "AND (:device IS NULL OR d.codigo = :device) " +
                "AND (:usuario IS NULL OR u.nome = :usuario) " +
                "ORDER BY r.data_hora ASC", 
        nativeQuery = true
    )
    public List<LocalDTO> findLocalByFilters( 
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("device") String nomeDevice,
        @Param("usuario") String nomeUsuario
    );    
}