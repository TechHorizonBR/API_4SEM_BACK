package br.fatec.bd4.repository;

import br.fatec.bd4.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

    Optional<Local> findByLatitudeAndLongitude(Double latitute, Double longitude);
    
}
