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

    @Transactional(readOnly = false)
    public void deleteById(Long id){
        try{
            demarcacaoRepository.deleteById(id);
        }catch(Exception exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not exist.");
        }
    }
}
