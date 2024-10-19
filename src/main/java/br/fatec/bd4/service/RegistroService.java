package br.fatec.bd4.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fatec.bd4.entity.Local;
import br.fatec.bd4.entity.Registro;
import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.repository.RegistroRepository;
import br.fatec.bd4.web.dto.MaxMinDTO;
import br.fatec.bd4.web.dto.RegisterDTO;
import br.fatec.bd4.web.dto.RegisterInputDTO;
import br.fatec.bd4.web.dto.RegistersResponseDTO;
import br.fatec.bd4.web.exception.EmptyFileException;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeParseException;

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
        if (file.isEmpty()) {
            throw new EmptyFileException("File is Empty");
        }
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;

            while ((line = buffer.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] fields = line.split(";");
                System.out.println(fields[4]);

                if (fields.length < 5 || fields[1].trim().isEmpty() || fields[2].trim().isEmpty()
                        || fields[3].trim().isEmpty() || fields[4].trim().isEmpty()) {
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

                    if (newDate != null) {
                        Local local = localService
                                .findByLatitudeAndLongitude(new Local(fields[6], latitude, longitude));

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

        for (RegisterInputDTO register : registers) {
            if (register.fullName() != null) {
                if (!register.fullName().equals("") && register.latitude() != null && register.longitude() != null
                        && !register.createdAt().equals("")) {
                    String name = register.fullName().trim();
                    String date = register.createdAt().trim();
                    date = date.replace(" ", "");
                    LocalDateTime newDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
                    Local local = localService.findByLatitudeAndLongitude(
                            new Local(register.localName(), register.latitude(), register.longitude()));
                    Usuario usuario = usuarioService.findByNameAndCreate(name);
                    registroRepository.save(new Registro(newDate, usuario, local));
                }
            }

        }
    }

    @Cacheable("registros")
    @Transactional(readOnly = true)
    public RegistersResponseDTO findLocalByFilters(String startDate, String endDate, Long idUsuario, int actualPage) {
        PageRequest pageRequest = PageRequest.of(actualPage, 100);
        Page<Registro> registrosPages = registroRepository.findLocalByFilters(startDate, endDate, idUsuario,
                pageRequest);
        MaxMinDTO coordinatesBounds = registroRepository.findMaxRegistro(startDate, endDate, idUsuario);
        List<RegisterDTO> registersDTO = new ArrayList<>();
        List<Registro> registros = registrosPages.getContent();
        int sizeList = registrosPages.getSize();
        boolean lastWasStopped = false;

        if (!registros.isEmpty()) {
            registersDTO.add(new RegisterDTO(registros.get(0).getDataHora(), registros.get(0).getLocal().getLatitude(),
                    registros.get(0).getLocal().getLongitude(), false));

            for (int i = 1; i < sizeList; i++) {
                boolean isStopped = true;
                while (isStopped) {
                    if (i >= registros.size())
                        break;

                    Registro registroAnterior = registros.get(i - 1);
                    Registro registroAtual = registros.get(i);

                    if (registroAtual.getLocal().getId().equals(registroAnterior.getLocal().getId())) {
                        long diffInMinutes = Duration
                                .between(registroAnterior.getDataHora(), registroAtual.getDataHora()).toMinutes();

                        if (diffInMinutes >= 15 && !lastWasStopped) {
                            isStopped = true;
                            registersDTO.add(
                                    new RegisterDTO(registroAtual.getDataHora(), registroAtual.getLocal().getLatitude(),
                                            registroAtual.getLocal().getLongitude(), true));
                            lastWasStopped = true;
                        } else {
                            lastWasStopped = false;
                            isStopped = false;
                        }
                        i++;
                        if (i >= sizeList)
                            break;
                    } else {
                        registersDTO.add(
                                new RegisterDTO(registroAtual.getDataHora(), registroAtual.getLocal().getLatitude(),
                                        registroAtual.getLocal().getLongitude(), false));
                        lastWasStopped = false;
                        break;
                    }
                }
            }
        }

        int totalPages = registrosPages.getTotalPages();
        return new RegistersResponseDTO(registersDTO, actualPage, totalPages, coordinatesBounds);
    }

}