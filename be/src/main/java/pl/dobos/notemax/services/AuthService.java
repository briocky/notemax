package pl.dobos.notemax.services;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.dobos.notemax.exceptions.AuthException;
import pl.dobos.notemax.models.dtos.LoginRequest;
import pl.dobos.notemax.models.dtos.LoginResponse;
import pl.dobos.notemax.models.dtos.RegisterRequest;
import pl.dobos.notemax.models.dtos.RegisterResponse;
import pl.dobos.notemax.models.dtos.UserDto;
import pl.dobos.notemax.models.dtos.keycloak.TokenResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

  private final CookieService cookieService;
  private final KeycloakAdminClientService keycloakAdminClientService;

  public RegisterResponse register(RegisterRequest request) {

    final UserDto requestUserDto = request.getUser();
    int registerStatus = keycloakAdminClientService.registerKeycloakUser(requestUserDto);

    if (registerStatus == HttpStatus.SC_CREATED) {
      log.debug("User registered successfully, getting token...");

      TokenResponse tokenResponse = keycloakAdminClientService.loginKeycloakUser(
          request.getUser().getEmail(), request.getUser().getPassword());
      Cookie refreshTokenCookie = cookieService.makeRefreshTokenCookie(
          tokenResponse.getRefreshToken());

      return RegisterResponse.builder()
          .token(tokenResponse.getToken())
          .refreshTokenCookie(refreshTokenCookie).build();
    } else if (registerStatus == HttpStatus.SC_CONFLICT) {
      throw new AuthException("User already exists!");
    } else {
      throw new AuthException("Unable to register the user!");
    }
  }

  public LoginResponse login(LoginRequest request) {
    TokenResponse tokenResponse = keycloakAdminClientService.loginKeycloakUser(
        request.getEmail(), request.getPassword());
    Cookie refreshTokenCookie = cookieService.makeRefreshTokenCookie(
        tokenResponse.getRefreshToken());

    return LoginResponse.builder()
        .token(tokenResponse.getToken())
        .refreshTokenCookie(refreshTokenCookie).build();
  }

  public LoginResponse refreshToken(String refreshToken) {
    TokenResponse tokenResponse = keycloakAdminClientService.refreshToken(refreshToken);
    Cookie refreshTokenCookie = cookieService.makeRefreshTokenCookie(
        tokenResponse.getRefreshToken());

    return LoginResponse.builder()
        .token(tokenResponse.getToken())
        .refreshTokenCookie(refreshTokenCookie).build();
  }
}
