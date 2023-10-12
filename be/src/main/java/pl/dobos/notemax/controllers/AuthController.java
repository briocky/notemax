package pl.dobos.notemax.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dobos.notemax.models.dtos.LoginRequest;
import pl.dobos.notemax.models.dtos.LoginResponse;
import pl.dobos.notemax.models.dtos.RefreshTokenResponse;
import pl.dobos.notemax.models.dtos.RegisterRequest;
import pl.dobos.notemax.models.dtos.RegisterResponse;
import pl.dobos.notemax.services.AuthService;
import pl.dobos.notemax.services.TokenService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final TokenService tokenService;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request,
      HttpServletResponse response) {
    RegisterResponse registerResponse = authService.register(request);
    response.addCookie(registerResponse.getRefreshTokenCookie());
    return ResponseEntity.created(null).body(registerResponse);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request,
      HttpServletResponse response) {
    LoginResponse loginResponse = authService.login(request);
    response.addCookie(loginResponse.getRefreshTokenCookie());
    return ResponseEntity.ok().body(loginResponse);
  }

  @GetMapping("/refreshToken")
  public ResponseEntity<RefreshTokenResponse> refreshToken(
      @CookieValue("refreshToken") @NotNull String refreshToken) {
    RefreshTokenResponse response = tokenService.refreshToken(refreshToken);
    return ResponseEntity.ok().body(response);
  }
}
