package br.fatec.bd4.service;

import br.fatec.bd4.entity.Device;
import br.fatec.bd4.entity.Local;
import br.fatec.bd4.entity.Registro;
import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.repository.RegistroRepository;
import br.fatec.bd4.web.dto.RegisterInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;
    @Autowired
    private LocalService localService;
    @Autowired
    private DeviceService deviceService;
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
            if(!register.fullName().equals("") && register.latitude() != null && register.longitude() != null && register.createdAt() != null){
                Local local = localService.findByLatitudeAndLongitude(new Local(register.localName(), register.latitude(), register.longitude()));
                Usuario usuario = usuarioService.findByNameAndCreate(register.fullName());
                registroRepository.save(new Registro(register.createdAt(), usuario, local));
            }
        }
    }
}

