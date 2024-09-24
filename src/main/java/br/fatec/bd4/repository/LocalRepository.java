package br.fatec.bd4.repository;

import br.fatec.bd4.entity.Local;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

    @Query(
        "SELECT l FROM Local l " +
        "INNER JOIN Registro r ON r.local = l.id " +
        "INNER JOIN Device d ON r.device_id = d.id " +
        "INNER JOIN User u ON d.usuario_id = u.id " +
        "WHERE r.data BETWEEN :startDate AND :endDate " +
        "AND d.codigo = :device " +
        "AND u.nome = :usuario " +
        "ORDER BY r.dataHora ASC "
    )
    public List<Local> findLocalByFilters( 
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("device")  String device,
        @Param("usuario") String usuario
        );
}