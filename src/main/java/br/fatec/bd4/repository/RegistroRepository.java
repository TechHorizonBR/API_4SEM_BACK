package br.fatec.bd4.repository;

import br.fatec.bd4.entity.Registro;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {

    @Query(value = "SELECT R FROM Registro R WHERE R.dataHora BETWEEN TO_TIMESTAMP(:startDate, 'YYYY-MM-DD HH24:MI:SS') AND TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI:SS') " +
                   "AND R.usuario.id = :idUsuario")
    List<Registro> findLocalByFilters( 
        @Param("startDate") String startDate,
        @Param("endDate") String endDate,
        @Param("idUsuario") Long idUsuario
    );    

}
