package br.fatec.bd4.service;

import br.fatec.bd4.entity.Registro;
import br.fatec.bd4.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;

    public List<Registro> findAll() {
        return registroRepository.findAll();
    }

    public Optional<Registro> findById(Long id) {
        return registroRepository.findById(id);
    }

    public Registro save(Registro registro) {
        return registroRepository.save(registro);
    }

    public void deleteById(Long id) {
        registroRepository.deleteById(id);
    }

    public Registro update(Long id, Registro registroDetails) {
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado com id " + id));
        registro.setDataHora(registroDetails.getDataHora());
        registro.setDevice(registroDetails.getDevice());
        registro.setLocal(registroDetails.getLocal());
        return registroRepository.save(registro);
    }
}

