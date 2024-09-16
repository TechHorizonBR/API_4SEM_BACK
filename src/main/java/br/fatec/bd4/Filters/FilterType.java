package br.fatec.bd4.Filters;


import java.util.List;

public class FilterType {
    private Filter filter;
    private List<String> values;

    public FilterType(Filter filter){
        this.filter = filter;
    }

    public List<String> getValues(){
        this.values = filter.getValues();
        return values;
    }
}
