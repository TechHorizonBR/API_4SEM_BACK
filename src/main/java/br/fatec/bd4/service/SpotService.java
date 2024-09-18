package br.fatec.bd4.service;

import br.fatec.bd4.entity.Spot;
import br.fatec.bd4.repository.SpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpotService {
    private final SpotRepository spotRepository;

    public Spot create(Spot spot){
        return spotRepository.save(spot);
    }
}
