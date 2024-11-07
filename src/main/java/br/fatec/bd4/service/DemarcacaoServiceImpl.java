package br.fatec.bd4.service;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.repository.DemarcacaoRepository;
import br.fatec.bd4.repository.UsuarioRepository;
import br.fatec.bd4.service.interfaces.DemarcacaoService;
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

    @Transactional(readOnly = false)
    public void deleteById(Long id){
        try{
            demarcacaoRepository.deleteById(id);
        }catch(Exception exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not exist.");
        }
    }
}
