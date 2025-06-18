package kata.bank_account.hexagone.builders;

import java.math.BigDecimal;
import kata.bank_account.hexagone.domain.CompteCourant;

public class CompteCourantBuilder {

  private String numeroCompte = "DEFAULT";
  private BigDecimal solde = BigDecimal.ZERO;
  private BigDecimal autorisationDeDecouvert = BigDecimal.ZERO;

  public static CompteCourantBuilder unCompte() {
    return new CompteCourantBuilder();
  }

  public CompteCourantBuilder avecNumeroCompte(String numero) {
    this.numeroCompte = numero;
    return this;
  }

  public CompteCourantBuilder avecSolde(BigDecimal soldeInitial) {
    this.solde = soldeInitial;
    return this;
  }

  public CompteCourant build() {
    CompteCourant compte = new CompteCourant(numeroCompte);
    if (solde.compareTo(BigDecimal.ZERO) > 0) {
      compte.deposer(solde);
    }
    compte.definirAutorisationDecouvert(autorisationDeDecouvert);
    return compte;
  }

  public CompteCourantBuilder avecAutorisationDecouvert(BigDecimal autorisationDeDecouvert) {
    this.autorisationDeDecouvert = autorisationDeDecouvert;
    return this;
  }
}
