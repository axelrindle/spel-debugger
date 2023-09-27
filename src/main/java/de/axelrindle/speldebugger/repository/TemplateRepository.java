package de.axelrindle.speldebugger.repository;

import de.axelrindle.speldebugger.entity.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateRepository extends MongoRepository<Template, String> {

    @Query("{name:{$regex:'?0'}}")
    List<Template> findByNameContains(String query);

    @Query("{name:'?0'}")
    Optional<Template> findByName(String name);

}
