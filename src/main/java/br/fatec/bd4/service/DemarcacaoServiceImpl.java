package br.fatec.bd4.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.repository.DemarcacaoRepository;
import br.fatec.bd4.repository.UsuarioRepository;
import br.fatec.bd4.service.interfaces.DemarcacaoService;
import br.fatec.bd4.web.dto.DemarcacaoDTO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DemarcacaoServiceImpl implements DemarcacaoService {

    @Autowired
    private final DemarcacaoRepository demarcacaoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Demarcacao> saveDemarcacoes(String nome, Long usuarioId, List<List<List<Double>>> polygonsCoordinates) {

        Optional<Usuario> optUsuario = usuarioRepository.findById(usuarioId);
        if (optUsuario.isEmpty()) {
            throw new IllegalArgumentException("Usuário inexistente");
        }

        Usuario usuario = optUsuario.get();
        GeometryFactory geometryFactory = new GeometryFactory();
        List<Demarcacao> demarcacoes = new ArrayList<>();

        for (List<List<Double>> coordinates : polygonsCoordinates) {
            Coordinate[] coordinateArray = coordinates.stream()
                    .map(coord -> new Coordinate(coord.get(0), coord.get(1)))
                    .toArray(Coordinate[]::new);

            LinearRing linearRing = geometryFactory.createLinearRing(coordinateArray);
            Polygon espacoGeometrico = geometryFactory.createPolygon(linearRing);

            if (!espacoGeometrico.isValid()) {
                throw new IllegalArgumentException("Geometria inválida para um dos polígonos");
            }

            Demarcacao demarcacao = new Demarcacao();
            demarcacao.setNome(nome);
            demarcacao.setEspaco_geometrico(espacoGeometrico);
            demarcacao.setUsuario(usuario);

            demarcacoes.add(demarcacaoRepository.save(demarcacao));
        }

        return demarcacoes;
    }

 @Override
public List<DemarcacaoDTO> getDemarcacaoByUsuarioId(Long usuarioId) {
    // Buscar as demarcações do usuário
    List<Demarcacao> demarcacoes = demarcacaoRepository.findDemarcacaoByUsuarioId(usuarioId);
    
    // Lista para armazenar os DTOs
    List<DemarcacaoDTO> demarcacaoDTOs = new ArrayList<>();
    
    // Iterar sobre as demarcações e converter para DTO
    for (Demarcacao demarcacao : demarcacoes) {
        
        // Extrair o polígono (supondo que demarcacao.getEspaco_geometrico() retorne um objeto Polygon)
        Polygon poligono = demarcacao.getEspaco_geometrico();
        
        // Converter as coordenadas do polígono para List<List<Double>>
        List<List<Double>> coordenadas = new ArrayList<>();
        
        // Verifica se o polígono não é nulo e tem coordenadas
        if (poligono != null && poligono.getCoordinates() != null) {
            for (Coordinate coordenada : poligono.getCoordinates()) {
                List<Double> ponto = new ArrayList<>();
                ponto.add(coordenada.getX()); // Longitude (ou X)
                ponto.add(coordenada.getY()); // Latitude (ou Y)
                coordenadas.add(ponto);
            }
        }
        
        DemarcacaoDTO dto = new DemarcacaoDTO(
            demarcacao.getId(),
            demarcacao.getNome(),          
            coordenadas                   
        );
        demarcacaoDTOs.add(dto);
    }
    
    return demarcacaoDTOs;
}

    @Transactional(readOnly = false)
    public void deleteById(Long id){
        try{
            demarcacaoRepository.deleteById(id);
        }catch(Exception exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not exist.");
        }
    }

    @Transactional
    public Demarcacao updateDemarcacao(Long id, String nome, Long usuarioId, List<List<List<Double>>> polygonsCoordinates) {
    
    Optional<Usuario> optUsuario = usuarioRepository.findById(usuarioId);
    if (optUsuario.isEmpty()) {
        throw new IllegalArgumentException("Usuário inexistente");
    }

    Optional<Demarcacao> optDemarcacao = demarcacaoRepository.findById(id);
    if (optDemarcacao.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Demarcação não encontrada");
    }

    Demarcacao demarcacao = optDemarcacao.get();
    Usuario usuario = optUsuario.get();

    demarcacao.setNome(nome);
    demarcacao.setUsuario(usuario);

    if (polygonsCoordinates != null && !polygonsCoordinates.isEmpty()) {
        GeometryFactory geometryFactory = new GeometryFactory();
        for (List<List<Double>> coordinates : polygonsCoordinates) {
            Coordinate[] coordinateArray = coordinates.stream()
                    .map(coord -> new Coordinate(coord.get(0), coord.get(1)))
                    .toArray(Coordinate[]::new);

            LinearRing linearRing = geometryFactory.createLinearRing(coordinateArray);
            Polygon espacoGeometrico = geometryFactory.createPolygon(linearRing);

            if (!espacoGeometrico.isValid()) {
                throw new IllegalArgumentException("Geometria inválida para um dos polígonos");
            }

            demarcacao.setEspaco_geometrico(espacoGeometrico);
        }
    }

    return demarcacaoRepository.save(demarcacao);
}
}
