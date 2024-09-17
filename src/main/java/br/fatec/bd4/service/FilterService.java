package br.fatec.bd4.service;

import br.fatec.bd4.enums.FiltersType;

import java.util.List;
import java.util.Set;

public interface FilterService {
    Set<String> getValuesByFilterType(FiltersType filter);
}
