package br.fatec.bd4.web.views;

import br.fatec.bd4.enums.FiltersType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
    private FiltersType tipo;
    private List<String> values;
}
