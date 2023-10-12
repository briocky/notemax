package pl.dobos.notemax.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dobos.notemax.models.entities.AccessToken;
import pl.dobos.notemax.models.entities.User;

@Repository
public interface AccessTokenRepository extends CrudRepository<AccessToken, Long> {

  Optional<AccessToken> findByOwner(User owner);
}
