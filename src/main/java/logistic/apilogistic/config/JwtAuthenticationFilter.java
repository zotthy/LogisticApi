package logistic.apilogistic.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import java.io.IOException;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

class JwtAuthenticationFilter extends HttpFilter {
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final RequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/auth", "POST", false);
    private final AuthenticationManager authenticationManager;
    private final AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
    private final JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!DEFAULT_ANT_PATH_REQUEST_MATCHER.matches(request)) {
            filterChain.doFilter(request, response);
        } else {
            try {
                Authentication authenticationResult = attemptAuthentication(request);
                logger.debug("Authentication success for user " + authenticationResult.getName());
                List<String> authorities = authenticationResult.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                String signedJWT = jwtService.createSignedJWT(authenticationResult.getName(), authorities);
                objectMapper.writeValue(response.getWriter(), new JwtWrapper(signedJWT));

            } catch (AuthenticationException e) {
                logger.debug("Authentication failed " + e.getMessage());
                this.failureHandler.onAuthenticationFailure(request, response, e);
            }
        }
    }

    private Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException, IOException {
        JwtAuthenticationToken jwtAuthentication = objectMapper.readValue(request.getInputStream(), JwtAuthenticationToken.class);
        logger.debug("Authenticating %s with password %s".formatted(jwtAuthentication.username, jwtAuthentication.password));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jwtAuthentication.username(), jwtAuthentication.password());
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    private record JwtAuthenticationToken(String username, String password){}
    private record JwtWrapper(String token){}
}