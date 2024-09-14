package br.fatec.bd4.Filters;

import java.util.ArrayList;
import java.util.List;

public class NameFilter implements Filter{
    private List<String> values = new ArrayList<>();

    //This part of code needs to connect to nosql database to pull the available users.
    @Override
    public List<String> getValues() {
        values.add("Names of users");

        return values;
    }
    
}
