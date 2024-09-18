package br.fatec.bd4.web.controller;


import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.entity.View;
import br.fatec.bd4.service.FilterServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/filters")
@CrossOrigin(value = "*")
@AllArgsConstructor
public class FilterController {
    private final FilterServiceImpl filterService;
    @GetMapping("/user/device")
    @JsonView(View.ViewFilterUserDevice.class)
    public List<Usuario> getUsersDevices(){
        return filterService.getUsersDevice();
    }

}
