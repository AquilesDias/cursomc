package com.aquilesd.coursemc.config;

import com.aquilesd.coursemc.security.JWTAuthenticationFilter;
import com.aquilesd.coursemc.security.JWTAuthorizationFilter;
import com.aquilesd.coursemc.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Permite anotação de pre-autorização
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Environment environment;

    @Autowired
    private JWTUtil jwtUtil;

    public static final String [] PUBLIC_MATCHERS_GET = {
            "/produtos/**",
            "/categorias/**"
    };
    public static final String [] PUBLIC_MATCHERS_POST = {
            "/clientes/**"
    };

    public static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**",
    };

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{

        //Pega o profile ativo e libera o que estiver no contains
        if(Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            httpSecurity.headers().frameOptions().disable();
        }

        httpSecurity.cors().and().csrf().disable();
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() //Acesso permitido somente metodo GET
                .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll() //Acesso permitido somente metodo POST
                .antMatchers(PUBLIC_MATCHERS).permitAll() // Todos caminhos que estão entre o parenteses, será permitido.
                .anyRequest().authenticated(); //Para o resto exige autenticação.
        httpSecurity.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
        httpSecurity.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //Assegura que backend não cria sessão de usuario.
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

