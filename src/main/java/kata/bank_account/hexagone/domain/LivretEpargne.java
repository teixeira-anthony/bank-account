package kata.bank_account.hexagone.domain;

import java.math.BigDecimal;
import kata.bank_account.hexagone.domain.exceptions.MontantDepotInvalideException;
import kata.bank_account.hexagone.domain.exceptions.MontantDepotMaximumAtteintException;
import kata.bank_account.hexagone.domain.exceptions.SoldeInsuffisantException;

public class LivretEpargne extends CompteBancaire {

  private final BigDecimal plafondDepot;

  // Constructeur complet
  public LivretEpargne(String numeroDeCompte, BigDecimal soldeInitial, BigDecimal plafondDepot) {
    super(numeroDeCompte, soldeInitial);
    if (plafondDepot == null || plafondDepot.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Le plafond de dépôt ne peut pas être négatif");
    }
    this.plafondDepot = plafondDepot;
  }

  // Constructeur par défaut avec 0 pour solde et plafond
  public LivretEpargne(String numeroDeCompte) {
    this(numeroDeCompte, BigDecimal.ZERO, BigDecimal.ZERO);
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

  public BigDecimal getPlafondDepot() {
    return plafondDepot;
  }

}