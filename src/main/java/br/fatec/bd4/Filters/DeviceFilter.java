package br.fatec.bd4.Filters;

import java.util.ArrayList;
import java.util.List;

public class DeviceFilter implements Filter{
    private List<String> values = new ArrayList<>();

    // This part of code needs to include the connection with nosql database to pull the available devices.
    @Override
    public List<String> getValues() {
        values.add("device");

        return values;
    }

    
}
