package kata.bank_account.hexagone.domain;

import java.math.BigDecimal;

public abstract class CompteBancaire {
  protected String numeroDeCompte;
  protected BigDecimal solde;

  public CompteBancaire(String numeroDeCompte) {
    this.numeroDeCompte = numeroDeCompte;
    this.solde = BigDecimal.ZERO;
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


