package com.veteroch4k.factory_service.configs;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
            .anyRequest().authenticated())
        .oauth2ResourceServer(oath2 -> oath2.jwt(Customizer.withDefaults()));

    return http.build();
  }

  /*
  Проблема в том, что когда мы получаем уведомление от кафки - там нету никакого токена
  и далее, при попытке сделать запрос к другому сервису через Feign нам нужно положить туда токен
  Поэтому наш Factory-service является ещё и клиентом, и сам обращается к киклоак, берет токен
  и добавляет в заголовок запроса через Feign
   */

  @Bean
  public RequestInterceptor oauth2FeignRequestInterceptor(
      ClientRegistrationRepository clientRegistrationRepository,
      OAuth2AuthorizedClientService authorizedClientService) {

    OAuth2AuthorizedClientProvider authorizedClientProvider =
        OAuth2AuthorizedClientProviderBuilder.builder()
            .clientCredentials()
            .build();

    AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
        new AuthorizedClientServiceOAuth2AuthorizedClientManager(
            clientRegistrationRepository, authorizedClientService);

    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

    return requestTemplate -> {
      OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
          .withClientRegistrationId("keycloak")
          .principal("factory-service")
          .build();

      OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);

      if (authorizedClient != null && authorizedClient.getAccessToken() != null) {
        String token = authorizedClient.getAccessToken().getTokenValue();
        requestTemplate.header("Authorization", "Bearer " + token);
      }
    };
  }

}
