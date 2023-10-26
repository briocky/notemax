package pl.dobos.notemax.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dobos.notemax.models.entities.Note;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

  Optional<Note> findNoteByIdAndAuthorId(Long id, String authorId);
  Page<Note> findAllByAuthorId(String authorId, Pageable pageable);
  boolean existsByIdAndAuthorId(Long id, String authorId);
}
