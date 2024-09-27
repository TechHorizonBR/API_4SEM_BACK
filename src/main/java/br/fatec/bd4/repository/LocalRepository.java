package br.fatec.bd4.repository;

import br.fatec.bd4.entity.Local;
import br.fatec.bd4.entity.Registro;

import java.util.List;
import java.time.LocalDateTime;
import org.springframework.data.repository.query.Param;
import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.fatec.bd4.web.dto.LocalDTO;
import java.util.Optional;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

    Optional<Local> findByLatitudeAndLongitude(Double latitute, Double longitude);
    
}
