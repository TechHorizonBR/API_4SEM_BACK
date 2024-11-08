package br.fatec.bd4.service.interfaces;

import java.util.List;

import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.web.dto.DemarcacaoDTO;

public interface DemarcacaoService {
    List<DemarcacaoDTO> getDemarcacaoByUsuarioId(Long usuarioId);
    List<Demarcacao> saveDemarcacoes(String nome, Long usuarioId, List<List<List<Double>>> polygonsCoordinates);
    Demarcacao updateDemarcacao(Long id, String nome, Long usuarioId, List<List<List<Double>>> polygonsCoordinates);
    void deleteById(Long id);
}


