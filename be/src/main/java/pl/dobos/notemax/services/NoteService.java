package pl.dobos.notemax.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.dobos.notemax.exceptions.NoteNotFoundException;
import pl.dobos.notemax.mappers.NoteMapper;
import pl.dobos.notemax.models.dtos.NoteDto;
import pl.dobos.notemax.models.entities.Note;
import pl.dobos.notemax.repositories.LinkRepository;
import pl.dobos.notemax.repositories.NoteRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {
  private static final String ELEMENT_CANNOT_BE_NULL = "%s cannot be null";

  private final NoteRepository noteRepository;
  private final LinkRepository linkRepository;
  private final CurrentUserService currentUserService;
  private final NoteMapper noteMapper;

  public List<NoteDto> getNotes(PageRequest pageRequest) {
    String currentUserId = currentUserService.getCurrentUserId();
    Page<Note> notes = noteRepository
        .findAllByAuthorId(currentUserId, pageRequest);
    return notes.map(noteMapper::getNoteDto).toList();
  }

  public NoteDto getNoteById(Long id) {
    nullCheck(id, String.format(ELEMENT_CANNOT_BE_NULL, "Id"));

    String currentUserId = currentUserService.getCurrentUserId();
    Note noteById = noteRepository.findNoteByIdAndAuthorId(id, currentUserId)
        .orElseThrow(() -> new NoteNotFoundException("Note not found"));

    return noteMapper.getNoteDto(noteById);
  }

  public NoteDto createNewNote(NoteDto noteDto) {
    nullCheck(noteDto, String.format(ELEMENT_CANNOT_BE_NULL, "Note"));

    String currentUserId = currentUserService.getCurrentUserId();

    Note note = noteMapper.getNote(noteDto);
    note.setAuthorId(currentUserId);
    setNoteCreationDateTime(note);
    Note savedNote = noteRepository.save(note);

    return noteMapper.getNoteDto(savedNote);
  }

  public NoteDto updateNote(Long id, NoteDto noteDto) {
    nullCheck(noteDto, String.format(ELEMENT_CANNOT_BE_NULL, "Note"));
    nullCheck(id, String.format(ELEMENT_CANNOT_BE_NULL, "Id"));

    String currentUserId = currentUserService.getCurrentUserId();

    Note noteById = noteRepository.findNoteByIdAndAuthorId(id, currentUserId)
        .orElseThrow(() -> new NoteNotFoundException("Note not found"));

    Note note = noteMapper.getNote(noteDto);
    note.setId(noteById.getId());
    note.setAuthorId(currentUserId);
    note.setCreatedAt(noteById.getCreatedAt());
    note.setModifiedAt(getCurrentDateTime());
    noteById.getLinks().forEach(oldLink ->
      note.getLinks().stream()
          .filter(link -> oldLink.getId().equals(link.getId()))
          .forEach(link -> linkRepository.deleteById(link.getId()))
    );
    Note savedNote = noteRepository.save(note);

    return noteMapper.getNoteDto(savedNote);
  }

  public void deleteNoteById(Long id) {
    nullCheck(id, String.format(ELEMENT_CANNOT_BE_NULL, "Id"));

    String currentUserId = currentUserService.getCurrentUserId();

    boolean noteExists = noteRepository.existsByIdAndAuthorId(id, currentUserId);
    if (!noteExists) {
      throw new NoteNotFoundException("Note not found");
    }

    noteRepository.deleteById(id);
  }

  private void nullCheck(Object arg, String message) {
    if (arg == null) {
      throw new IllegalArgumentException(message);
    }
  }

  private void setNoteCreationDateTime(Note note) {
    LocalDateTime now = getCurrentDateTime();
    if (note.getCreatedAt() == null || !note.getCreatedAt().isBefore(now)) {
      note.setCreatedAt(now);
    }
  }

  private LocalDateTime getCurrentDateTime() {
    final String warsawTimeZone = "Europe/Warsaw";
    return LocalDateTime.now(ZoneId.of(warsawTimeZone));
  }
}
