package ma.rest.spring.entities;
import org.springframework.data.rest.core.config.Projection;
/** Vue solde demandée par le paramètre projection. */
@Projection(name="solde",types=Compte.class)
public interface CompteProjection1 { double getSolde(); }
