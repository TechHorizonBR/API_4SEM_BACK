package br.fatec.bd4.Filters;

import br.fatec.bd4.repository.SpotRepository;
import lombok.val;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NameFilter implements Filter{
    private Set<String> values = new HashSet<>();

    private SpotRepository spotRepository;

    public NameFilter(SpotRepository spotRepository){
        this.spotRepository = spotRepository;
    }

    //This part of code needs to connect to nosql database to pull the available users.
    @Override
    public Set<String> getValues() {
        this.values = spotRepository.findAllFullNames("Card_0FF0");
        return values;
    }
    
}
