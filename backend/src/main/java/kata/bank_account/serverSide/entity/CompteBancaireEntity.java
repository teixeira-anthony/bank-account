package kata.bank_account.serverSide.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type_de_compte")
public abstract class CompteBancaireEntity {

  @Id
  private String numeroDeCompte;

  private BigDecimal solde;

  public String getNumeroDeCompte() {
    return numeroDeCompte;
  }

  public void setNumeroDeCompte(String numeroDeCompte) {
    this.numeroDeCompte = numeroDeCompte;
  }

  public BigDecimal getSolde() {
    return solde;
  }

  public void setSolde(BigDecimal solde) {
    this.solde = solde;
  }
}
