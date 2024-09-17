package br.fatec.bd4.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import br.fatec.bd4.entity.Spot;
import java.util.List;
import java.util.Set;

public interface SpotRepository extends MongoRepository<Spot, String> {

    @Query(value = "{'codeDevice': ?0}", fields = "{'fullName': 1, '_id': 0}")
    Set<String> findAllFullNames(String codeDevice);
}
