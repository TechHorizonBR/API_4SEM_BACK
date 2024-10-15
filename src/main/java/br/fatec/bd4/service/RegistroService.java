package br.fatec.bd4.service;

import br.fatec.bd4.entity.Local;
import br.fatec.bd4.entity.Registro;
import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.repository.RegistroRepository;
import br.fatec.bd4.web.dto.RegisterDTO;
import br.fatec.bd4.web.dto.RegisterInputDTO;
import br.fatec.bd4.web.dto.RegistersResponseDTO;
import br.fatec.bd4.web.exception.EmptyFileException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public boolean isValidLatitude(double latitude) {
        return latitude >= -90 && latitude <= 90;
    }
    
    public boolean isValidLongitude(double longitude) {
        return longitude >= -180 && longitude <= 180;
    }
    
    @Transactional
    public void inputRegistersByUploadFile(MultipartFile file) {
        if (file.isEmpty()){
            throw new EmptyFileException("File is Empty");
        }
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
    
            while((line = buffer.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
    
                String[] fields = line.split(";");
                System.out.println(fields[4]);
    
                if (fields.length < 5 || fields[1].trim().isEmpty() || fields[2].trim().isEmpty() || fields[3].trim().isEmpty() || fields[4].trim().isEmpty()) {
                    continue;
                }
    
                String fullName = fields[4].trim();
                String createdAt = fields[1].trim();
                double latitude = 0;
                double longitude = 0;
    
                try {
                    latitude = Double.parseDouble(fields[2].trim());
                    longitude = Double.parseDouble(fields[3].trim());
                    
                } catch (NumberFormatException e) {
                    continue;
                }
    
                if (isValidLatitude(latitude) && isValidLongitude(longitude)) {
                    LocalDateTime newDate = null;
                    
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                        newDate = LocalDateTime.parse(createdAt, formatter);
                    } catch (DateTimeParseException e) {
                        continue;
                    }
    
                    if(newDate != null) {
                        Local local = localService.findByLatitudeAndLongitude(new Local(fields[6], latitude, longitude));
    
                        Usuario usuario = usuarioService.findByNameAndCreate(fullName);
    
                        registroRepository.save(new Registro(newDate, usuario, local));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar o arquivo CSV", e);
        }
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


