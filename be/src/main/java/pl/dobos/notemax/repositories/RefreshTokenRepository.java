package pl.dobos.notemax.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dobos.notemax.models.entities.RefreshToken;
import pl.dobos.notemax.models.entities.User;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByOwner(User owner);
}
