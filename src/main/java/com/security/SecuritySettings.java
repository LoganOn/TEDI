package com.security;

import com.security.JWT.JWTConfigurer;
import com.security.JWT.JWTTokenProvider;
import com.security.JWT.RestAuthenticationEntryPoint;
import com.service.CustomUserDetailsService;
import com.service.JWTBlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecuritySettings extends WebSecurityConfigurerAdapter {

  private final CustomUserDetailsService customUserDetailsService;

  private final PasswordEncoder passwordEncoder;

  private final JWTTokenProvider jwtTokenProvider;

  private final JWTBlackListService jwtBlackListService;

  @Bean
  public HttpFirewall allowSemicolonHttpFirewall() {
    StrictHttpFirewall firewall = new StrictHttpFirewall();
    firewall.setAllowSemicolon(true);
    return firewall;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public WebMvcConfigurer corsConfigurerWebProfile() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000"
                )
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*");
      }
    };
  }


  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder);
  }

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.csrf().disable()
//            .anonymous().authorities("ROLE_ANONYMOUS")
//            .and()
//            .authorizeRequests()//.antMatchers(HttpMethod.OPTIONS,"*/").permitAll()
//            .antMatchers(HttpMethod.POST,"/login").permitAll();
//  }
@Override
protected void configure(HttpSecurity http) throws Exception {
  http
          .cors()
          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .csrf()
          .disable()
          .formLogin()
          .disable()
          .httpBasic()
          .disable()
          .exceptionHandling()
          .authenticationEntryPoint(new RestAuthenticationEntryPoint())
          .and()
          .authorizeRequests()
          .antMatchers("/api/login")
          .permitAll()
          .antMatchers("/api/**").access("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_SUPPLIER')")
          .anyRequest()
  .authenticated();
  http.apply(new JWTConfigurer(jwtTokenProvider, jwtBlackListService));
//  if (Arrays.asList(this.environment.getActiveProfiles()).contains("dep")) {
//    http.requiresChannel()
//            .anyRequest()
//            .requiresSecure();
  }

}
