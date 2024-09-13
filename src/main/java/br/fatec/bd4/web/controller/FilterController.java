package br.fatec.bd4.web.controller;

import br.fatec.bd4.enums.FiltersType;
import br.fatec.bd4.service.FilterServiceImpl;
import br.fatec.bd4.web.views.FilterView;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/filters")
@AllArgsConstructor
public class FilterController {
    private final FilterServiceImpl filterService;

    @GetMapping("/type")
    public ResponseEntity<List<String>> getByTypeFilter(@RequestBody FilterView filter){
        return ResponseEntity.ok().body(filterService.getValuesByFilterType(filter.getFilter()));
    }
}
