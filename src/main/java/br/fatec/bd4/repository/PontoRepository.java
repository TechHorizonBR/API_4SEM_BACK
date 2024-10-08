package br.fatec.bd4.repository;

import br.fatec.bd4.entity.Ponto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoRepository extends JpaRepository<Ponto, Long> {
    // Você pode adicionar métodos personalizados aqui, se necessário
}
