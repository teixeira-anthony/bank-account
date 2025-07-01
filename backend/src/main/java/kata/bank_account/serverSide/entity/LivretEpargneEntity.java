package kata.bank_account.serverSide.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("EPARGNE")
public class LivretEpargneEntity extends CompteBancaireEntity {

  private BigDecimal plafondDepot;

  public BigDecimal getPlafondDepot() {
    return plafondDepot;
  }

  public void setPlafondDepot(BigDecimal plafondDepot) {
    this.plafondDepot = plafondDepot;
  }
}
