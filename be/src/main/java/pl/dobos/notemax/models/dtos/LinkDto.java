package pl.dobos.notemax.models.dtos;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class LinkDto {

  Long id;
  @NotBlank
  String url;
}
