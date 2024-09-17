package br.fatec.bd4.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import br.fatec.bd4.document.Spot;

import java.util.Set;

public interface SpotRepository extends MongoRepository<Spot, String> {

    @Query(value = "{}", fields = "{'fullName': 1, '_id': 0}")
    Set<String> findAllFullNames();

    @Query(value = "{}", fields = "{'codeDevice':  1, '_id': 0}")
    Set<String> findAllCodeDevice();
}
