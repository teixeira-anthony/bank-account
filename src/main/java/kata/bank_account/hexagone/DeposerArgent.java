package kata.bank_account.hexagone;

import java.math.BigDecimal;
import kata.bank_account.hexagone.domain.port.CompteBancairePort;

public class DeposerArgent {
private final CompteBancairePort compteBancairePort;

  public DeposerArgent(CompteBancairePort compteBancairePort) {
    this.compteBancairePort = compteBancairePort;
  }

  public void execute(String numeroDeCompte, BigDecimal montant) {

    var compteBancaire = compteBancairePort.recupererCompte(numeroDeCompte);
    compteBancaire.deposer(montant);
    compteBancairePort.sauvegarderCompte(compteBancaire);
  }
}
