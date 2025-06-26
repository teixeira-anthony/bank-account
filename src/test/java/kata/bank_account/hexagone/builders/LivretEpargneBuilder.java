package kata.bank_account.hexagone.builders;

import java.math.BigDecimal;
import kata.bank_account.hexagone.domain.LivretEpargne;

public class LivretEpargneBuilder {

  private String numeroCompte = "DEFAULT";
  private BigDecimal solde = BigDecimal.ZERO;
  private BigDecimal plafondDeDepot = BigDecimal.ZERO;

  public static LivretEpargneBuilder unLivret() {
    return new LivretEpargneBuilder();
  }

  public LivretEpargneBuilder avecNumeroCompte(String numero) {
    this.numeroCompte = numero;
    return this;
  }

  public LivretEpargneBuilder avecSolde(BigDecimal soldeInitial) {
    this.solde = soldeInitial;
    return this;
  }

  public LivretEpargne build() {
    LivretEpargne compte = new LivretEpargne(numeroCompte, BigDecimal.ZERO, plafondDeDepot);

    if (solde.compareTo(BigDecimal.ZERO) > 0) {
      compte.deposer(solde);
    }
    return compte;
  }


  public LivretEpargneBuilder avecPlafondDeDepot(BigDecimal plafondDeDepot) {
    this.plafondDeDepot = plafondDeDepot;
    return this;
  }
}
