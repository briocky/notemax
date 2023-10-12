package pl.dobos.notemax.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.dobos.notemax.services.JwtTokenUtils;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

  private final JwtTokenUtils jwtTokenUtils;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    final String authHeaderPrefix = "Bearer ";
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authHeader != null && authHeader.startsWith(authHeaderPrefix)) {
      final String token = authHeader.substring(authHeaderPrefix.length());
      if (jwtTokenUtils.isTokenExpired(token)) {
        log.info("Token expired");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("Token expired");
        return;
      }
      final String email = jwtTokenUtils.extractEmail(token);
      final UserDetails loadedUser = userDetailsService.loadUserByUsername(email);
      final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          loadedUser, null, loadedUser.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      log.info("Token verified");
    } else {
      log.info("Token not found");
      filterChain.doFilter(request, response);
    }
  }
}
