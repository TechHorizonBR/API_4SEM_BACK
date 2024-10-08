package br.fatec.bd4.service;

import br.fatec.bd4.entity.Ponto;
import br.fatec.bd4.repository.PontoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PontoService {

    private final PontoRepository pontoRepository;

public PontoService(PontoRepository pontoRepository) {
    this.pontoRepository = pontoRepository;
}

public Ponto salvarPonto(Ponto ponto) {
    return pontoRepository.save(ponto);
}

public List<Ponto> listarPontos() {
    return pontoRepository.findAll();
}

public Optional<Ponto> buscarPontoPorId(Long id) {
    return pontoRepository.findById(id);
}

public void deletarPonto(Long id) {
    pontoRepository.deleteById(id);
}
}