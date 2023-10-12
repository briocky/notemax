package pl.dobos.notemax.mappers;

import io.jsonwebtoken.Claims;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import pl.dobos.notemax.models.entities.AccessToken;
import pl.dobos.notemax.models.entities.RefreshToken;
import pl.dobos.notemax.models.entities.User;
import pl.dobos.notemax.services.JwtTokenUtils;

@Mapper(componentModel = "spring")
public abstract class TokenMapper {

  private static final String ZONE_NAME = "Europe/Warsaw";
  @Autowired
  protected JwtTokenUtils jwtTokenUtils;

  public AccessToken getAccessToken(String token, User owner) {

    Date issuedAt = jwtTokenUtils.extractClaim(token, Claims::getIssuedAt);
    Date expirationDate = jwtTokenUtils.extractClaim(token, Claims::getExpiration);

    return AccessToken.builder()
        .owner(owner)
        .value(token)
        .issueDate(LocalDateTime.ofInstant(issuedAt.toInstant(), ZoneId.of(ZONE_NAME)))
        .expirationDate(LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.of(ZONE_NAME)))
        .build();
  }

  public RefreshToken getRefreshToken(String token, User owner) {

    Date issuedAt = jwtTokenUtils.extractClaim(token, Claims::getIssuedAt);
    Date expirationDate = jwtTokenUtils.extractClaim(token, Claims::getExpiration);

    return RefreshToken.builder()
        .owner(owner)
        .value(token)
        .issueDate(LocalDateTime.ofInstant(issuedAt.toInstant(), ZoneId.of(ZONE_NAME)))
        .expirationDate(LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.of(ZONE_NAME)))
        .build();
  }
}
