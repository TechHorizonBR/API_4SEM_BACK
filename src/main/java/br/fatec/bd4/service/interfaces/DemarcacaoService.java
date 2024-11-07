package br.fatec.bd4.service.interfaces;

import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.web.dto.DemarcacaoDTO;

import java.util.List;

public interface DemarcacaoService {
List<Demarcacao> saveDemarcacoes(String nome, Long usuarioId, List<List<List<Double>>> polygonsCoordinates);
}
