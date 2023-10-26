package pl.dobos.notemax.mappers;

import org.mapstruct.Mapper;
import pl.dobos.notemax.models.dtos.NoteDto;
import pl.dobos.notemax.models.entities.Note;

@Mapper(componentModel = "spring")
public interface NoteMapper {

  NoteDto getNoteDto(Note source);
  Note getNote(NoteDto source);
}
