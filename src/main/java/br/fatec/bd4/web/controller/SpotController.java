package br.fatec.bd4.web.controller;

import br.fatec.bd4.entity.Spot;
import br.fatec.bd4.service.SpotService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spots")
@CrossOrigin("*")
@AllArgsConstructor
public class SpotController {

    private final SpotService spotService;

    @PostMapping()
    public ResponseEntity<Spot> create(@RequestBody Spot spot){
        return ResponseEntity.ok().body(spotService.create(spot));
    }
    
}
