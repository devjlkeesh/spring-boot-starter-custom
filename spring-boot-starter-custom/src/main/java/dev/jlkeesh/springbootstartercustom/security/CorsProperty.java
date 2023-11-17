package dev.jlkeesh.springbootstartercustom.security;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * @author: Javohir Elmurodov
 * @since: Nov 17, 2023 6:29:27 PM
 */

@Getter
@Setter
@ConditionalOnMissingBean(CorsProperty.class)
@ConfigurationProperties(prefix = "cors.config")
public class CorsProperty {
  private List<String> allowedHeaders;
  private List<String> allowedOrigins;
  private List<String> allowedMethods;
  private List<String> exposedHeaders;
  private String urlPattern;
  private boolean allowCredentials;
}
