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

import java.util.Optional;

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
//        demarc.setUsuario(user.get());

        return demarcacaoRepository.save(demarc);
    }
}
