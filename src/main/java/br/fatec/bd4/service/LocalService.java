package br.fatec.bd4.service;

import br.fatec.bd4.entity.Local;
import br.fatec.bd4.repository.LocalRepository;
import org.hibernate.boot.jaxb.internal.stax.LocalSchemaLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

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
        local.setLatitude(localDetails.getLatitude());
        return localRepository.save(local);
    }
    @Transactional
    public Local findByLatitudeAndLongitude(Local local){
        Optional<Local> localFound = localRepository.findByLatitudeAndLongitude(local.getLatitude(), local.getLongitude());

        if (localFound.isEmpty()){
            return localRepository.save(new Local(local.getNome(), local.getLatitude(), local.getLongitude()));
        }else{
            return localFound.get();
        }
    }
}
