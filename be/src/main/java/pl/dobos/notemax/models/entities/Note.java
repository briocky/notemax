package pl.dobos.notemax.models.entities;

import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pl.dobos.notemax.models.enums.NoteCategory;

@Entity
@Table(name = "notes")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Note {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_id_seq")
  @SequenceGenerator(name = "note_id_seq", sequenceName = "note_id_seq", allocationSize = 1)
  Long id;
  @NotNull
  String title;
  @NotNull
  String content;
  @NotNull
  String authorId;
  @Enumerated(EnumType.STRING)
  NoteCategory category;
  LocalDateTime createdAt;
  LocalDateTime modifiedAt;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "note_id")
  List<Link> links;
}
