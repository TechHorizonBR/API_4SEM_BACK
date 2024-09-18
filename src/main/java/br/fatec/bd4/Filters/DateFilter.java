package br.fatec.bd4.Filters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DateFilter implements Filter {
    private Set<String> values = new HashSet<>();

    // Talk to team if will need to include more periods
    @Override
    public Set<String> getValues() {
        values.add("Last one hour");
        values.add("Last 3 days");
        values.add("Last week");
        values.add("Last Month");

        return values;
    }
    
}
