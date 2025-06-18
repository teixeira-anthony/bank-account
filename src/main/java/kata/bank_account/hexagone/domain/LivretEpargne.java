package kata.bank_account.hexagone.domain;

import java.math.BigDecimal;
import kata.bank_account.hexagone.domain.exceptions.MontantDepotInvalideException;
import kata.bank_account.hexagone.domain.exceptions.MontantDepotMaximumAtteintException;
import kata.bank_account.hexagone.domain.exceptions.SoldeInsuffisantException;

public class LivretEpargne extends CompteBancaire {
  private BigDecimal plafondDepot;

  public LivretEpargne(String numeroDeCompte) {
    super(numeroDeCompte);
    this.plafondDepot = BigDecimal.ZERO;
  }

  @Override
  public void deposer(BigDecimal montant) {
    if (montant.compareTo(BigDecimal.ZERO) <= 0) {
      throw new MontantDepotInvalideException("Montant doit être positif");
    }
    BigDecimal nouveauSolde = solde.add(montant);
    if (nouveauSolde.compareTo(plafondDepot) > 0) {
      throw new MontantDepotMaximumAtteintException("Le dépôt dépasse le plafond du livret");
    }
    solde = nouveauSolde;
  }

  @Override
  public void retirer(BigDecimal montantARetirer) {
    if (montantARetirer.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Montant doit être positif");
    }
    if (solde.compareTo(montantARetirer) < 0) {
      throw new SoldeInsuffisantException("Fonds insuffisants");
    }
    solde = solde.subtract(montantARetirer);
  }

  public void definirPlafondDeDepot(BigDecimal montant) {
    if (montant.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("L'autorisation de découvert ne peut pas être négative");
    }
    this.plafondDepot = montant;
  }

  public BigDecimal getPlafondDepot() {
    return plafondDepot;
  }
}
