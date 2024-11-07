package br.fatec.bd4.service;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Demarcacao saveDemarcacao() {
        Demarcacao demarc = new Demarcacao();
//        Optional<Usuario> user = usuarioRepository.findById(75L);
        GeometryFactory geometryFactory = new GeometryFactory();

        Coordinate[] coordinates = new Coordinate[]{
                new Coordinate(50.0, 50.0),
                new Coordinate(100.0, 50.0),
                new Coordinate(100.0, 100.0),
                new Coordinate(50.0, 100.0),
                new Coordinate(50.0, 50.0)
        };

        LinearRing linearRing = geometryFactory.createLinearRing(coordinates);

        Polygon espacoGeometrico = geometryFactory.createPolygon(linearRing);


        // demarc.setId(1L);
        if (espacoGeometrico.isValid()) {
            demarc.setNome("Espaco teste");
            demarc.setEspaco_geometrico(espacoGeometrico);
        } else {
            throw new IllegalArgumentException("Geometria inválida");
        }
 //       demarc.setUsuario(user.get());

        return demarcacaoRepository.save(demarc);
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
        
        // Criar o DTO
        DemarcacaoDTO dto = new DemarcacaoDTO(
            demarcacao.getId(),
            demarcacao.getNome(),          
            coordenadas                   
        );
    
        // Adicionar o DTO na lista
        demarcacaoDTOs.add(dto);
    }
    
    // Retornar a lista de DTOs
    return demarcacaoDTOs;
}

    }
