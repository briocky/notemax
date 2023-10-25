package pl.dobos.notemax.models.dtos.keycloak;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@FieldDefaults(level = PRIVATE)
public class TokenResponse {

  @JsonProperty("access_token")
  String token;
  @JsonProperty("refresh_token")
  String refreshToken;
  @JsonProperty("expires_in")
  String expiresIn;
  @JsonProperty("refresh_expires_in")
  String refreshExpiresIn;
  @JsonProperty("token_type")
  String tokenType;
  @JsonProperty("not-before-policy")
  String notBeforePolicy;
  @JsonProperty("session_state")
  String sessionState;
  @JsonProperty("scope")
  String scope;
}
