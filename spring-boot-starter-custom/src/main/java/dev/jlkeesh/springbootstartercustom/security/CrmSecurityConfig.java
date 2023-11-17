package dev.jlkeesh.springbootstartercustom.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfigurationSource;

/*
 * @author: Javohir Elmurodov
 * @since: Nov 17, 2023 6:04:57 PM
 */

@Slf4j
@Configuration
// @EnableConfigurationProperties(MongoProperties.class)
@ConditionalOnMissingBean(type = "org.springframework.security.web.SecurityFilterChain")
@EnableAutoConfiguration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class CrmSecurityConfig {

  private final String[] whiteList;
  private final ObjectMapper objectMapper;
  private final CorsConfigurationSource corsConfigurationSource;

  public CrmSecurityConfig(
      @Qualifier("whiteList") String[] whiteList, ObjectMapper objectMapper) {
    this.whiteList = whiteList;
    this.objectMapper = objectMapper;
  }

  @Bean
  public SecurityFilterChain httpFilterChain(HttpSecurity http) throws Exception {

    return http.csrf(AbstractHttpConfigurer::disable)
        .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(
            httpReq ->
                httpReq.requestMatchers(whiteList).permitAll().anyRequest().fullyAuthenticated())
        .build();
  }

  @Bean
  @ConditionalOnMissingBean(name = "WHITE_LIST")
  public String[] whiteList() {
    return new String[] {"/**"};
  }

  private AuthenticationEntryPoint getAuthenticationEntryPoint() {
    return (request, response, e) -> {
      log.error("Unauthorized", e);
      response.setStatus(401);
      ServletOutputStream outputStream = response.getOutputStream();
      objectMapper.writeValue(outputStream, Map.of("error", "UNAUTHORIZED", "status", 401));
    };
  }

  private AccessDeniedHandler getAccessDeniedHandler() {
    return (request, response, e) -> {
      log.error("AccessDeniedException", e);
      response.setStatus(403);
      ServletOutputStream outputStream = response.getOutputStream();
      objectMapper.writeValue(outputStream, Map.of("error", "Access Denied", "status", 403));
    };
  }
}
