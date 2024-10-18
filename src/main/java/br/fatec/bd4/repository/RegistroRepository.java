package br.fatec.bd4.repository;

import br.fatec.bd4.entity.Registro;
import br.fatec.bd4.web.dto.MaxMinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {

    @Query("SELECT new br.fatec.bd4.web.dto.MaxMinDTO(" +
            "MAX(R.local.latitude), MAX(R.local.longitude), MIN(R.local.latitude), MIN(R.local.longitude)) " +
            "FROM Registro R WHERE R.dataHora BETWEEN TO_TIMESTAMP(:startDate, 'YYYY-MM-DD HH24:MI:SS') AND TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI:SS') "
            + "AND R.usuario.id = :idUsuario ")
    MaxMinDTO findMaxRegistro(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("idUsuario") Long idUsuario);

    @Query("SELECT R FROM Registro R WHERE R.dataHora BETWEEN TO_TIMESTAMP(:startDate, 'YYYY-MM-DD HH24:MI:SS') AND TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI:SS') "
            +
            "AND R.usuario.id = :idUsuario " +
            "ORDER BY R.dataHora ASC")
            
    Page<Registro> findLocalByFilters(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("idUsuario") Long idUsuario,
            Pageable pageable);

}
