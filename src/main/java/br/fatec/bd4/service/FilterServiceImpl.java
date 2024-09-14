package br.fatec.bd4.service;

import br.fatec.bd4.Filters.DateFilter;
import br.fatec.bd4.Filters.DeviceFilter;
import br.fatec.bd4.Filters.NameFilter;
import br.fatec.bd4.Filters.FilterType;
import br.fatec.bd4.enums.FiltersType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterServiceImpl implements FilterService {
    @Override
    public List<String> getValuesByFilterType(FiltersType filter) {

        switch (filter){
            case DATE:
                return new FilterType(new DateFilter()).getValues();
            case DEVICE:
                return new FilterType(new DeviceFilter()).getValues();
            case NAME:
                return new FilterType(new NameFilter()).getValues();
            default:
                throw new RuntimeException("Wrong filter");
        }
    }
}
