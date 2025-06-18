package kata.bank_account.hexagone;

import java.math.BigDecimal;
import kata.bank_account.hexagone.domain.port.CompteBancairePort;

public class RetirerArgent {
  private final CompteBancairePort compteBancairePort;

  public RetirerArgent(CompteBancairePort compteBancairePort) {
    this.compteBancairePort = compteBancairePort;
  }

  public void execute(String numeroDeCompte, BigDecimal montantARetirer){
    var compte = compteBancairePort.recupererCompte(numeroDeCompte);
    compte.retirer(montantARetirer);
    compteBancairePort.sauvegarderCompte(compte);
  }
}
