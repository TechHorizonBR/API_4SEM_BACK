package br.fatec.bd4.repository;

import br.fatec.bd4.entity.Registro;

import br.fatec.bd4.web.dto.MaxMinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {


    @Query(
        "SELECT new br.fatec.bd4.web.dto.MaxMinDTO("+
        "MAX(R.local.latitude), MAX(R.local.longitude), MIN(R.local.latitude), MIN(R.local.longitude)) " +
        "FROM Registro R WHERE R.usuario.id = :idUsuario"
    )
    MaxMinDTO findMaxRegistro(@Param("idUsuario") Long idUsuario);

    @Query(
            value = "SELECT * FROM registro R WHERE R.data_hora BETWEEN TO_TIMESTAMP(:startDate, 'YYYY-MM-DD HH24:MI:SS') AND TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI:SS') " +
                    "AND R.usuario_id = :idUsuario " +
                    "ORDER BY R.data_hora ASC",
            nativeQuery = true
    )
    Page<Registro> findLocalByFilters(
    @Param("startDate") String startDate,
    @Param("endDate") String endDate,
    @Param("idUsuario") Long idUsuario,
    Pageable pageable
);

}
