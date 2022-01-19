package com.example.examensarbete.security;

import com.example.examensarbete.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.examensarbete.Model.Role.RoleConstant.*;

/**
 * The configurations-class for the security
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTIssuer jwtIssuer;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTIssuer jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Configure the authorization for the user by mail
     * @param auth
     * @throws Exception when the user is not found
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((username) -> userRepository.getByGoogleId(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found")))
                .passwordEncoder(passwordEncoder);
    }

    /**
     * Configure for our Spring Security, sets the what endpoins respective role should have access to
     * @param http
     * @throws Exception when there is no role
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        final JWTAuthorizationFilter jwtAuthorizationFilter= new JWTAuthorizationFilter(authenticationManager(), jwtIssuer);
        final CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManager(), jwtIssuer);
        final CORSFilter corsFilter = new CORSFilter();

        // mest generella antMatchers först
        http.csrf().disable()
                .authorizeRequests()
               // .antMatchers("/admin/**").hasRole(ADMIN.name())
                .antMatchers("/user/**").hasRole(USER.name())
                .antMatchers("/movie/**").permitAll()
                .antMatchers("/movie/update/**").hasRole(USER.name())
                .antMatchers("/series/**").permitAll()
                .antMatchers("/series/update/**").hasRole(USER.name())
               // .antMatchers("/public/getUser").hasAnyRole(USER.name(), ADMIN.name(),SUPER_ADMIN.name())
                .antMatchers("/**").hasRole(SUPER_ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(corsFilter, CustomAuthenticationFilter.class)
                .addFilterBefore(authenticationFilter, JWTAuthorizationFilter.class)
                .addFilter(jwtAuthorizationFilter)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
