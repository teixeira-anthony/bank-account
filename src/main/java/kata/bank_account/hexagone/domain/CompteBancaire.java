package kata.bank_account.hexagone.domain;

import java.math.BigDecimal;

public abstract class CompteBancaire {
  protected String numeroDeCompte;
  protected BigDecimal solde;

  protected CompteBancaire(String numeroDeCompte, BigDecimal soldeInitial) {
    this.numeroDeCompte = numeroDeCompte;
    this.solde = soldeInitial != null ? soldeInitial : BigDecimal.ZERO;
  }

  public BigDecimal recupererSolde() {
    return solde;
  }

  public String recupererNumeroDeCompte() {
    return numeroDeCompte;
  }

  public abstract void deposer(BigDecimal montant);
  public abstract void retirer(BigDecimal montantARetirer);
}



