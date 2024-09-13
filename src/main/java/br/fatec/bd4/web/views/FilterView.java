package br.fatec.bd4.web.views;

import br.fatec.bd4.enums.FiltersType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilterView {
    private FiltersType filter;
}
