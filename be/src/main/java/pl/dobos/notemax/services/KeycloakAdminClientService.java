package pl.dobos.notemax.services;

import jakarta.ws.rs.core.Response;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import pl.dobos.notemax.config.KeycloakProvider;
import pl.dobos.notemax.exceptions.AuthException;
import pl.dobos.notemax.models.dtos.User;
import pl.dobos.notemax.models.dtos.keycloak.TokenResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakAdminClientService {

  private static final String TOKEN_URI = "/realms/%s/protocol/openid-connect/token";
  private final KeycloakProvider keycloakProvider;

  public int registerKeycloakUser(User requestUser) {
    UsersResource usersResource = keycloakProvider.getRealmResource().users();
    UserRepresentation userRepresentation = createUserRepresentation(requestUser);

    try (Response registerResponse = usersResource.create(userRepresentation)) {
      return registerResponse.getStatus();
    }
  }

  public TokenResponse loginKeycloakUser(String email, String password) {
    return keycloakProvider.getWebClient()
        .post()
        .uri(String.format(TOKEN_URI, keycloakProvider.getRealm()))
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(prepareGetTokenParams(email, password)))
        .retrieve()
        .bodyToMono(TokenResponse.class)
        .block();
  }

  public TokenResponse refreshToken(String refreshToken) {
    return keycloakProvider.getWebClient()
        .post()
        .uri(String.format(TOKEN_URI, keycloakProvider.getRealm()))
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(prepareRefreshTokenParams(refreshToken)))
        .retrieve()
        .onStatus(
            status -> status.value() == HttpStatus.BAD_REQUEST.value(),
            clientResponse -> {
              throw new AuthException("Invalid refresh token!");
            })
        .bodyToMono(TokenResponse.class)
        .block();
  }

  private static CredentialRepresentation createPasswordCredentials(String password) {
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue(password);
    return passwordCredentials;
  }

  private static UserRepresentation createUserRepresentation(User user) {
    CredentialRepresentation credentialRepresentation = createPasswordCredentials(
        user.getPassword());

    UserRepresentation kcUser = new UserRepresentation();
    kcUser.setUsername(user.getEmail());
    kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
    kcUser.setFirstName(user.getFirstName());
    kcUser.setLastName(user.getLastName());
    kcUser.setEmail(user.getEmail());
    kcUser.setEnabled(true);
    kcUser.setEmailVerified(false);

    return kcUser;
  }

  private MultiValueMap<String, String> prepareGetTokenParams(String email, String password) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", OAuth2Constants.PASSWORD);
    params.add("client_id", keycloakProvider.getClientID());
    params.add("client_secret", keycloakProvider.getClientSecret());
    params.add("username", email);
    params.add("password", password);
    return params;
  }

  private MultiValueMap<String, String> prepareRefreshTokenParams(String refreshToken) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", OAuth2Constants.REFRESH_TOKEN);
    params.add("refresh_token", refreshToken);
    params.add("client_id", keycloakProvider.getClientID());
    params.add("client_secret", keycloakProvider.getClientSecret());
    return params;
  }
}
