package br.fatec.bd4.service;

import br.fatec.bd4.entity.Local;
import br.fatec.bd4.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalService {

    @Autowired
    private LocalRepository localRepository;

    public List<Local> findAll() {
        return localRepository.findAll();
    }

    public Optional<Local> findById(Long id) {
        return localRepository.findById(id);
    }

    public Local save(Local local) {
        return localRepository.save(local);
    }

    public void deleteById(Long id) {
        localRepository.deleteById(id);
    }

    public Local update(Long id, Local localDetails) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Local not found with id " + id));
        local.setNome(localDetails.getNome());
        local.setLongitude(localDetails.getLongitude());
        local.setAltitude(localDetails.getAltitude());
        return localRepository.save(local);
    }
}