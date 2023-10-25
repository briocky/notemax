package pl.dobos.notemax.config;

import lombok.Getter;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Getter
public class KeycloakProvider {

  @Value("${keycloak.auth-server-url}")
  private String serverURL;
  @Value("${keycloak.realm}")
  private String realm;
  @Value("${keycloak.resource}")
  private String clientID;
  @Value("${keycloak.credentials.secret}")
  private String clientSecret;

  private Keycloak keycloak;
  private WebClient keycloakWebClient;

  public Keycloak getKeycloak() {
    if(keycloak == null) {
      keycloak = KeycloakBuilder.builder()
          .serverUrl(serverURL)
          .realm(realm)
          .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
          .clientId(clientID)
          .clientSecret(clientSecret)
          .build();
    }
    return keycloak;
  }

  public RealmResource getRealmResource() {
    return getKeycloak().realm(realm);
  }

  public WebClient getWebClient() {
    if(keycloakWebClient == null) {
      keycloakWebClient = WebClient.builder()
          .baseUrl(serverURL)
          .build();
    }
    return keycloakWebClient;
  }
}
