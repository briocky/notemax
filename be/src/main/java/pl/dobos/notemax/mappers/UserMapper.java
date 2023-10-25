package pl.dobos.notemax.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.dobos.notemax.models.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "enabled", constant = "true")
  @Mapping(target = "authorities", ignore = true)
  User getUser(pl.dobos.notemax.models.dtos.User source);
}
