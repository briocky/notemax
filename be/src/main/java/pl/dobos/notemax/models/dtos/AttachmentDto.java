package pl.dobos.notemax.models.dtos;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pl.dobos.notemax.models.enums.AttachmentType;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class AttachmentDto {

  Long id;
  AttachmentType type;
  String url;
}
