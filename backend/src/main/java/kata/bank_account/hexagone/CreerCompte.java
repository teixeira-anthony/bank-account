package kata.bank_account.hexagone;

import java.util.UUID;
import kata.bank_account.hexagone.domain.CompteBancaire;
import kata.bank_account.hexagone.domain.CompteCourant;
import kata.bank_account.hexagone.domain.LivretEpargne;
import kata.bank_account.hexagone.domain.TypeDeCompteEnum;
import kata.bank_account.hexagone.domain.port.CompteBancairePort;

public class CreerCompte {

  private final CompteBancairePort compteBancairePort;

  public CreerCompte(CompteBancairePort compteBancairePort) {
    this.compteBancairePort = compteBancairePort;
  }

  public String executer(TypeDeCompteEnum typeDeCompteEnum){
    CompteBancaire compteBancaire;
    var numeroCompte = UUID.randomUUID().toString();
    switch (typeDeCompteEnum){
      case COURANT -> compteBancaire = new CompteCourant(numeroCompte);
      case EPARGNE -> compteBancaire = new LivretEpargne(numeroCompte);
      default -> throw new IllegalArgumentException("Type de compte non valide");
    }
    compteBancairePort.sauvegarderCompte(compteBancaire);

    return numeroCompte;
  }
}
