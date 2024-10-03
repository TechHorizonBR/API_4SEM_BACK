package br.fatec.bd4.service;

import br.fatec.bd4.entity.Local;
import br.fatec.bd4.entity.Registro;
import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.repository.RegistroRepository;
import br.fatec.bd4.web.dto.RegisterDTO;
import br.fatec.bd4.web.dto.RegisterInputDTO;
import br.fatec.bd4.web.dto.RegistersResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;
    @Autowired
    private LocalService localService;
    @Autowired
    private UsuarioService usuarioService;

    public List<Registro> findAll() {
        return registroRepository.findAll();
    }

    public Optional<Registro> findById(Long id) {
        return registroRepository.findById(id);
    }

    public Registro save(Registro registro) {
        return registroRepository.save(registro);
    }

    public void deleteById(Long id) {
        registroRepository.deleteById(id);
    }


    @Transactional
    public void inputRegisters(List<RegisterInputDTO> registers) {
        for(RegisterInputDTO register : registers){
            if(register.fullName()!= null){
                if(!register.fullName().equals("") && register.latitude() != null && register.longitude() != null && !register.createdAt().equals("")){
                    String name = register.fullName().trim();
                    String date = register.createdAt().trim();
                    date = date.replace(" ", "");
                    LocalDateTime newDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
                    Local local = localService.findByLatitudeAndLongitude(new Local(register.localName(), register.latitude(), register.longitude()));
                    Usuario usuario = usuarioService.findByNameAndCreate(name);
                    registroRepository.save(new Registro(newDate, usuario, local));
                }
            }
            
        }
    }

    @Cacheable("registros")
    @Transactional(readOnly = true)
    public RegistersResponseDTO findLocalByFilters(String startDate, String endDate , Long idUsuario, int currentPage) {

        PageRequest pageRequest = PageRequest.of(currentPage, 10);

        Page<Registro> registrosPages = registroRepository.findLocalByFilters(startDate, endDate, idUsuario, pageRequest);

        Set<String> uniqueCoordinates = new HashSet<>();

        List<RegisterDTO> registrosDto =  registrosPages.stream()
                .filter(registro -> {
                    String coordinatesKey = registro.getLocal().getLatitude() + "," + registro.getLocal().getLongitude();
                    if (uniqueCoordinates.contains(coordinatesKey)) {
                        return false;
                    } else {
                        uniqueCoordinates.add(coordinatesKey);
                        return true;
                    }
                })
                .map(registro -> new RegisterDTO(
                        registro.getDataHora(),
                        registro.getLocal().getLatitude(),
                        registro.getLocal().getLongitude()
                ))
                .collect(Collectors.toList());

        int pageActual = registrosPages.getNumber();
        int totalPages = registrosPages.getTotalPages();

        return new RegistersResponseDTO(registrosDto, pageActual, totalPages);
    }

}


