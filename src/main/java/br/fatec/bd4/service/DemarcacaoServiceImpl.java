package br.fatec.bd4.service;

import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.entity.Device;
import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.repository.DemarcacaoRepository;
import br.fatec.bd4.repository.UsuarioRepository;
import br.fatec.bd4.service.interfaces.DemarcacaoService;
import br.fatec.bd4.web.dto.DemarcacaoDTO;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

}
