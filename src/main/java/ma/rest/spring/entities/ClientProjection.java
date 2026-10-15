package ma.rest.spring.entities;

import org.springframework.data.rest.core.config.Projection;

/** Vue clientDetails demandée par le paramètre projection. */
@Projection(name = "clientDetails", types = Client.class)
public interface ClientProjection {
  String getNom();

  String getEmail();
}
