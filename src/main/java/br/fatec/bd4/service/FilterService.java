package br.fatec.bd4.service;

import br.fatec.bd4.enums.FiltersType;

import java.util.List;

public interface FilterService {
    List<String> getValuesByFilterType(FiltersType filter);
}
