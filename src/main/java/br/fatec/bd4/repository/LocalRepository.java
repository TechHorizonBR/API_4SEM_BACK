package br.fatec.bd4.repository;

import br.fatec.bd4.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {
    // Você pode adicionar métodos personalizados aqui, se necessário
}
