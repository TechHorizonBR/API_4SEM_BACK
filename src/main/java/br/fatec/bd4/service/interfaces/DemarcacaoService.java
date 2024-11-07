package br.fatec.bd4.service.interfaces;

import java.util.List;

import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.web.dto.DemarcacaoDTO;

public interface DemarcacaoService {
//    DemarcacaoDTO saveDemarcacao(DemarcacaoDTO demarcacaoDTO);
    Demarcacao saveDemarcacao();

    List<DemarcacaoDTO> getDemarcacaoByUsuarioId(Long usuarioId);
}


