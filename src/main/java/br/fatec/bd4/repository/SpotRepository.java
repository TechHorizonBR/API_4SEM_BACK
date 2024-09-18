package br.fatec.bd4.repository;

import br.fatec.bd4.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SpotRepository extends JpaRepository<Spot, Long> {

}
