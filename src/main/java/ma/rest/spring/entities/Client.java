package ma.rest.spring.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

/** Relation inverse : le compte porte la clé étrangère du client. */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nom;
  private String email;

  @OneToMany(mappedBy = "client")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<Compte> comptes = new ArrayList<>();
}
