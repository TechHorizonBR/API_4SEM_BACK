package br.fatec.bd4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.bd4.entity.Demarcacao;

public interface DemarcacaoRepository extends JpaRepository<Demarcacao, Long> {
    List<Demarcacao>findDemarcacaoByUsuarioId(Long id);

}
