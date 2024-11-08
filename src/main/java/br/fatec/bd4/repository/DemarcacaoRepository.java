package br.fatec.bd4.repository;

import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.entity.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DemarcacaoRepository extends JpaRepository<Demarcacao, Long> {
    List<Demarcacao>findDemarcacaoByUsuarioId(Long id);

}
