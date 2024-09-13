package br.fatec.bd4.Filters;

import java.util.ArrayList;
import java.util.List;

public class DateFilter implements Filter {
    private List<String> values = new ArrayList<>();

    // Talk to team if will need to include more periods
    @Override
    public List<String> getValues() {
        values.add("Last one hour");
        values.add("Last 3 days");
        values.add("Last week");
        values.add("Last Month");

        return values;
    }
    
}
