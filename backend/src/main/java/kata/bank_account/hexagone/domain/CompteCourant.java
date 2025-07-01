package kata.bank_account.hexagone.domain;

import java.math.BigDecimal;
import kata.bank_account.hexagone.domain.exceptions.MontantDepotInvalideException;
import kata.bank_account.hexagone.domain.exceptions.SoldeInsuffisantException;

public class CompteCourant extends CompteBancaire {

  private final BigDecimal autorisationDecouvert;

  // Constructeur complet
  public CompteCourant(String numeroDeCompte, BigDecimal soldeInitial, BigDecimal autorisationDecouvert) {
    super(numeroDeCompte, soldeInitial);
    if (autorisationDecouvert == null || autorisationDecouvert.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("L'autorisation de découvert ne peut pas être négative");
    }
    this.autorisationDecouvert = autorisationDecouvert;
  }

  // Constructeur par défaut avec 0 pour solde et découvert
  public CompteCourant(String numeroDeCompte) {
    this(numeroDeCompte, BigDecimal.ZERO, BigDecimal.ZERO);
  }

  @Override
  public void deposer(BigDecimal montant) {
    if (montant.compareTo(BigDecimal.ZERO) <= 0) {
      throw new MontantDepotInvalideException("Le montant déposé doit être strictement positif.");
    }
    solde = solde.add(montant);
  }

  @Override
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
}

