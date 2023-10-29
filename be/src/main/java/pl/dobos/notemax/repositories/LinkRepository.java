package pl.dobos.notemax.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dobos.notemax.models.entities.Link;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {

}
