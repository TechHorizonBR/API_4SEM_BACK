package br.fatec.bd4.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fatec.bd4.entity.Local;
import br.fatec.bd4.entity.Registro;
import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.repository.RegistroRepository;
import br.fatec.bd4.web.dto.RegisterDTO;
import br.fatec.bd4.web.dto.RegisterInputDTO;
import br.fatec.bd4.web.dto.RegistersResponseDTO;

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

    public List<Registro> findLocalByFilters(String startDate, String endDate, Long idUsuario) {
        return registroRepository.saveAll(null);
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
public boolean compareRegisters(String startDate, String endDate, Long idUsuario) {
    PageRequest pageRequest = PageRequest.of(0, 10);

    Page<Registro> registrosPages = registroRepository.findLocalByFilters(startDate, endDate, idUsuario);

    List<Registro> registros = registrosPages.getContent();

    for (int i = 0; i < registros.size() - 1; i++) {
        Registro primeiroRegistro = registros.get(i);
        Registro segundoRegistro = registros.get(i + 1);

   
    if (primeiroRegistro.getLocal().getLatitude() == segundoRegistro.getLocal().getLatitude()) {
           
            long diffInMillis = Math.abs(primeiroRegistro.getDataHora().getSecond() - segundoRegistro.getDataHora().getSecond());
            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);

        if (diffInMinutes > 15) {
            return true; 
        }
    }
}
    
    return false; 
}

public boolean isStopped(String startDate, String endDate, Long idUsuario) {
    return compareRegisters(startDate, endDate, idUsuario);
}
        public RegistersResponseDTO findLocalByFilters(String startDate, String endDate, Long idUsuario, int actualPage) {
        PageRequest pageRequest = PageRequest.of(actualPage, 10);
        Page<Registro> registrosPages = registroRepository.findLocalByFilters(startDate, endDate, idUsuario);

        List<RegisterDTO> registrosDto = registrosPages.stream()
            .map(registro -> new RegisterDTO(
                registro.getDataHora(),
                registro.getLocal().getLatitude(),
                registro.getLocal().getLongitude()
            ))
            .collect(Collectors.toList());

        int totalPages = registrosPages.getTotalPages();
        return new RegistersResponseDTO(registrosDto, actualPage, totalPages, false); // Inicialmente isStopped é false
    }

}


