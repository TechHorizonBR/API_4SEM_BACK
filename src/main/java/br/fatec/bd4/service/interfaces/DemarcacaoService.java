package br.fatec.bd4.service.interfaces;

import java.util.List;

import br.fatec.bd4.entity.Demarcacao;

public interface DemarcacaoService {
//    DemarcacaoDTO saveDemarcacao(DemarcacaoDTO demarcacaoDTO);
    Demarcacao saveDemarcacao();

    List<Demarcacao> getDemarcacaoByUsuarioId(Long usuarioId);
}


