package kata.bank_account.serverSide.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("COURANT")
public class CompteCourantEntity extends CompteBancaireEntity {

  private BigDecimal autorisationDecouvert;

  public BigDecimal getAutorisationDecouvert() {
    return autorisationDecouvert;
  }

  public void setAutorisationDecouvert(BigDecimal autorisationDecouvert) {
    this.autorisationDecouvert = autorisationDecouvert;
  }
}
