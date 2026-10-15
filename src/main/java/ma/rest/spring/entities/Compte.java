package ma.rest.spring.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.Date;

/** Compte persisté ; le type est stocké en texte pour rendre la base lisible. */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compte {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @PositiveOrZero private double solde;

  @Temporal(TemporalType.DATE)
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
  @NotNull
  private Date dateCreation;

  @Enumerated(EnumType.STRING)
  @NotNull
  private TypeCompte type;

  @ManyToOne
  @JoinColumn(name = "client_id")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Client client;
}
