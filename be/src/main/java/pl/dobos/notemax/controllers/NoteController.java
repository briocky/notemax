package pl.dobos.notemax.controllers;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dobos.notemax.models.dtos.NoteDto;
import pl.dobos.notemax.services.NoteService;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Validated
class NoteController {

  private final NoteService noteService;

  @GetMapping(value = "/all", params = { "page", "size" })
  public ResponseEntity<List<NoteDto>> getNotes(
      @RequestParam("page") int page,
      @RequestParam("size") int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    List<NoteDto> notes = noteService.getNotes(pageRequest);
    return ResponseEntity.ok(notes);
  }

  @GetMapping("/{id}")
  public ResponseEntity<NoteDto> getNote(@PathVariable Long id) {
    return ResponseEntity.ok(noteService.getNoteById(id));
  }

  @PostMapping("/new")
  public ResponseEntity<NoteDto> createNote(@RequestBody @Valid NoteDto noteDto) {
    NoteDto note = noteService.createNewNote(noteDto);
    return ResponseEntity
        .created(URI.create("/api/notes/" + note.getId()))
        .body(note);
  }

  @PutMapping("/{id}")
  public ResponseEntity<NoteDto> updateNote(
      @PathVariable Long id,
      @RequestBody @Valid NoteDto noteDto) {
    NoteDto note = noteService.updateNote(id, noteDto);
    return ResponseEntity
        .created(URI.create("/api/notes/" + note.getId()))
        .body(note);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
    noteService.deleteNoteById(id);
    return ResponseEntity.noContent().build();
  }
}
