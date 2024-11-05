package br.fatec.bd4.service;

import br.fatec.bd4.entity.Demarcacao;
import br.fatec.bd4.entity.Usuario;
import br.fatec.bd4.repository.DemarcacaoRepository;
import br.fatec.bd4.service.interfaces.DemarcacaoService;
import br.fatec.bd4.web.dto.DemarcacaoDTO;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DemarcacaoServiceImpl implements DemarcacaoService {

    @Autowired
    private final DemarcacaoRepository demarcacaoRepository;

    @Override
    public Demarcacao saveDemarcacao() {
        Demarcacao demarc = new Demarcacao();
        Usuario user = new Usuario();
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

        user.setId(74L);

        demarc.setNome("Espaco teste");
        demarc.setEspaco_geometrico(espacoGeometrico);
        demarc.setUsuario(user);

        return demarcacaoRepository.save(demarc);
    }
}
