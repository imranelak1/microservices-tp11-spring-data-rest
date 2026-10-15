package ma.rest.spring.repositories;

import ma.rest.spring.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/** Opérations de persistance déléguées à Spring Data JPA. */
@RepositoryRestResource(
    path = "comptes",
    collectionResourceRel = "comptes",
    itemResourceRel = "compte")
public interface CompteRepository extends JpaRepository<Compte, Long> {
  @RestResource(path = "byType")
  List<Compte> findByType(@Param("t") TypeCompte type);
}
