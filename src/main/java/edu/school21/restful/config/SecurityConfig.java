package edu.school21.restful.config;

import edu.school21.restful.security.JWTAuthenticationEntryPoint;
import edu.school21.restful.security.JWTAuthenticationFilter;
import edu.school21.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserService userService;

    @Autowired
    public SecurityConfig(JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint, UserService userService) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/swagger-ui/**", "/explorer/**", "/signUp").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/users",
                        "/courses",
                        "/courses/*",
                        "/courses/*/lessons",
                        "/courses/*/students",
                        "/courses/*/teachers").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                .antMatchers(HttpMethod.POST,
                        "/users",
                        "/courses",
                        "/courses/*/lessons",
                        "/courses/*/students",
                        "/courses/*/teachers").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,
                        "/users/*",
                        "/courses/*",
                        "/courses/*/lessons/*",
                        "/courses/*/publish").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,
                        "/users/*",
                        "/courses/*",
                        "/courses/*/lessons/*",
                        "/courses/*/students/*",
                        "/courses/*/teachers/*").hasRole("ADMIN")
                .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
