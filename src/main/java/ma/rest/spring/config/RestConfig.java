package ma.rest.spring.config;

import ma.rest.spring.entities.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/** Expose explicitement les identifiants, sans écrire de contrôleur CRUD. */
@Configuration
public class RestConfig implements RepositoryRestConfigurer {
  public void configureRepositoryRestConfiguration(
      RepositoryRestConfiguration config, CorsRegistry cors) {
    config.exposeIdsFor(Compte.class, Client.class);
    config.setReturnBodyOnCreate(true);
    config.setReturnBodyOnUpdate(true);
  }
}
