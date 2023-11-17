package dev.jlkeesh.springbootstartercustom.security;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/*
 * @author: Javohir Elmurodov
 * @since: Nov 17, 2023 6:30:46 PM
 */

@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties({CorsProperty.class})
public class SecurityConfigAutoConfiguration {

  private final CorsProperty corsProperty;

  public SecurityConfigAutoConfiguration(CorsProperty corsProperty) {
    this.corsProperty = corsProperty;
  }

  @Bean
  @ConditionalOnMissingBean(CorsConfigurationSource.class)
  public CorsConfigurationSource corsConfigurationSource() {
    return getUrlBasedCorsConfigurationSource();
  }

  @Bean
  @ConditionalOnMissingBean(CorsConfigurationSource.class)
  public CorsFilter corsFilter() {
    return new CorsFilter(getUrlBasedCorsConfigurationSource());
  }

  private UrlBasedCorsConfigurationSource getUrlBasedCorsConfigurationSource() {
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
        new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.registerCorsConfiguration(
        corsProperty.getUrlPattern(), corsConfiguration());
    return urlBasedCorsConfigurationSource;
  }

  private CorsConfiguration corsConfiguration() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedHeaders(corsProperty.getAllowedHeaders());
    corsConfiguration.setAllowedOrigins(corsProperty.getAllowedOrigins());
    corsConfiguration.setAllowedMethods(corsProperty.getAllowedMethods());
    corsConfiguration.setAllowCredentials(corsProperty.isAllowCredentials());
    corsConfiguration.setExposedHeaders(corsProperty.getExposedHeaders());
    return corsConfiguration;
  }
}
