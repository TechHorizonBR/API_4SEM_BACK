package br.fatec.bd4.Filters;

import br.fatec.bd4.repository.SpotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;

import java.util.*;
import java.util.stream.Collectors;

public class NameFilter implements Filter{
    private Set<String> values = new HashSet<>();

    private SpotRepository spotRepository;

    public NameFilter(SpotRepository spotRepository){
        this.spotRepository = spotRepository;
    }

    //This part of code needs to connect to nosql database to pull the available users.
    @Override
    public Set<String> getValues() {
        ObjectMapper objectMapper = new ObjectMapper();
        this.values = spotRepository.findAllFullNames()
                .stream()
                .map(jsonString -> {
                    try {
                        // Converte a string JSON em um Map
                        Map<String, String> map = objectMapper.readValue(jsonString, Map.class);
                        return map.get("fullName");
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(fullName -> fullName != null)
                .collect(Collectors.toSet());

        return values;
    }
    
}
