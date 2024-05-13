package logistic.apilogistic.config;


import logistic.apilogistic.security.BearerTokenFilter;
import logistic.apilogistic.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
class SecurityConfig {
    private final JwtService jwtService;

    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        BearerTokenFilter bearerTokenFilter = new BearerTokenFilter(jwtService);
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(mvc.pattern("/register")).permitAll()
                .requestMatchers(mvc.pattern("/login")).permitAll()

                .requestMatchers(mvc.pattern("/cargo/dis")).hasRole("USER")
                .requestMatchers(mvc.pattern("/cargo/{id}")).hasRole("USER")
                .requestMatchers(mvc.pattern("/cargo/add")).hasRole("USER")

                .requestMatchers(mvc.pattern("/cargo")).permitAll()
                .requestMatchers(mvc.pattern("/orders/cargo/")).hasRole("USER")
                .requestMatchers(mvc.pattern("/cargo/{cargoId}/take/{driverId}")).hasRole("USER")

                .requestMatchers(mvc.pattern("/profile")).hasRole("USER")
                .requestMatchers(mvc.pattern("/profile/address")).hasRole("USER")
                .requestMatchers(mvc.pattern("/checkProfile/{email}")).hasRole("USER")


                .requestMatchers(mvc.pattern("/driverNew")).hasRole("USER")
                .requestMatchers(mvc.pattern("/drivers")).hasRole("USER")
                .requestMatchers(mvc.pattern("/driver/{id}")).hasRole("USER")

                .requestMatchers(mvc.pattern("/cargos")).permitAll()
                .anyRequest().permitAll()
        );
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(csrfCustomizer -> csrfCustomizer.disable());
        http.addFilterBefore(bearerTokenFilter, AuthorizationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
