package pl.dobos.notemax.config;

import java.util.Collection;
import java.util.Map;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthConverter implements Converter<Jwt, JwtAuthenticationToken> {

  private static final String REALM_ACCESS_CLAIM = "realm_access";
  private static final String ROLES_CLAIM = "roles";
  private static final String DEFAULT_AUTHORITY_PREFIX = "ROLE_";

  @Override
  public JwtAuthenticationToken convert(Jwt jwt) {
    Map<String, Collection<String>> realmAccess = jwt.getClaim(REALM_ACCESS_CLAIM);
    Collection<String> roles = realmAccess.get(ROLES_CLAIM);
    var grantedAuthorities = roles.stream()
        .map(role -> new SimpleGrantedAuthority(DEFAULT_AUTHORITY_PREFIX + role))
        .toList();
    String principalClaimValue = jwt.getSubject();
    return new JwtAuthenticationToken(jwt, grantedAuthorities, principalClaimValue);
  }
}
