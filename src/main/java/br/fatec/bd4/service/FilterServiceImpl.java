package br.fatec.bd4.service;

import br.fatec.bd4.Filters.DateFilter;
import br.fatec.bd4.Filters.DeviceFilter;
import br.fatec.bd4.Filters.NameFilter;
import br.fatec.bd4.Filters.FilterType;
import br.fatec.bd4.enums.FiltersType;
import br.fatec.bd4.repository.SpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class FilterServiceImpl implements FilterService {
    private final SpotRepository spotRepository;
    @Override
    public Set<String> getValuesByFilterType(FiltersType filter) {

        switch (filter){
            case DATE:
                return new FilterType(new DateFilter()).getValues();
            case DEVICE:
                return new FilterType(new DeviceFilter()).getValues();
            case NAME:
                return new FilterType(new NameFilter(spotRepository)).getValues();
            default:
                throw new RuntimeException("Wrong filter");
        }
    }
}
