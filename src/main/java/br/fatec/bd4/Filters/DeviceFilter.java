package br.fatec.bd4.Filters;

import br.fatec.bd4.repository.SpotRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

public class DeviceFilter implements Filter{
    private Set<String> values = new HashSet<>();

    private SpotRepository spotRepository;

    public DeviceFilter(SpotRepository spotRepository){
        this.spotRepository = spotRepository;
    }

    // This part of code needs to include the connection with nosql database to pull the available devices.
    @Override
    public Set<String> getValues() {
        ObjectMapper objectMapper = new ObjectMapper();
        this.values = spotRepository.findAllCodeDevice()
                .stream()
                .map(jsonString-> {
                    try {
                        Map<String, String> map = objectMapper.readValue(jsonString, Map.class);
                        return map.get("codeDevice");
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(codeDevice -> codeDevice != null)
                .collect(Collectors.toSet());
        return values;
    }

    
}
