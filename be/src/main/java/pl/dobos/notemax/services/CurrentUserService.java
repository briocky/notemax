package pl.dobos.notemax.services;

import java.security.Principal;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.dobos.notemax.exceptions.EmptySecurityContextException;

@Service
@Slf4j
public class CurrentUserService {

  public Jwt getCurrentUserToken() {
    return getCurrentUserTokenFromSecurityContext()
        .orElseThrow(() -> new EmptySecurityContextException("No user logged in"));
  }

  public String getCurrentUserId() {
    return getCurrentUserIdFromSecurityContext()
        .orElseThrow(() -> new EmptySecurityContextException("No user logged in"));
  }

  private Optional<Jwt> getCurrentUserTokenFromSecurityContext() {
    return Optional
        .ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .map(authentication -> ((JwtAuthenticationToken)authentication).getToken());
  }

  private Optional<String> getCurrentUserIdFromSecurityContext() {
    return Optional
        .ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .map(Principal::getName);
  }
}
