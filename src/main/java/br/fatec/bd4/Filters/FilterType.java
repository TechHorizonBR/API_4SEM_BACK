package br.fatec.bd4.Filters;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterType {
    private Filter filter;
    private Set<String> values = new HashSet<>();

    public FilterType(Filter filter){
        this.filter = filter;
    }

    public Set<String> getValues(){
        this.values = filter.getValues();
        return values;
    }
}
