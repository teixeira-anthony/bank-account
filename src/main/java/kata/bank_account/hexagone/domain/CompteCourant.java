package kata.bank_account.hexagone.domain;

import java.math.BigDecimal;
import kata.bank_account.hexagone.domain.exceptions.MontantDepotInvalideException;
import kata.bank_account.hexagone.domain.exceptions.SoldeInsuffisantException;

public class CompteCourant extends CompteBancaire {

  private BigDecimal autorisationDecouvert;

  public CompteCourant(String numeroDeCompte) {
    super(numeroDeCompte);
    this.autorisationDecouvert = BigDecimal.ZERO;
  }

  @Override
  public void deposer(BigDecimal montant) {
    if (montant.compareTo(BigDecimal.ZERO) <= 0) {
      throw new MontantDepotInvalideException("Le montant déposé doit être strictement positif.");
    }
    solde = solde.add(montant);
  }

  public void retirer(BigDecimal montantARetirer) {
    BigDecimal soldeApresRetrait = solde.subtract(montantARetirer);
    BigDecimal limiteDecouvert = autorisationDecouvert.negate();
    if (soldeApresRetrait.compareTo(limiteDecouvert) < 0) {
      throw new SoldeInsuffisantException("Fonds insuffisants pour effectuer le retrait.");
    }
    solde = soldeApresRetrait;
  }

  public BigDecimal recupererAutorisationDecouvert() {
    return autorisationDecouvert;
  }

  public void definirAutorisationDecouvert(BigDecimal montant) {
    if (montant.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("L'autorisation de découvert ne peut pas être négative");
    }
    this.autorisationDecouvert = montant;
  }
}
