package pl.dobos.notemax.models.dtos;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pl.dobos.notemax.models.enums.NoteCategory;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class NoteDto {

  Long id;
  @NotBlank
  String title;
  @NotBlank
  String content;
  @JsonIgnore
  String authorId;
  NoteCategory category;
  LocalDateTime createdAt;
  LocalDateTime modifiedAt;
  List<AttachmentDto> attachments;
}
