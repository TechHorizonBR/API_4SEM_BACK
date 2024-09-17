package br.fatec.bd4.Filters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeviceFilter implements Filter{
    private Set<String> values = new HashSet<>();

    // This part of code needs to include the connection with nosql database to pull the available devices.
    @Override
    public Set<String> getValues() {
        values.add("device");

        return values;
    }

    
}
