package kata.bank_account.userSide;

import kata.bank_account.hexagone.domain.CompteBancaire;
import kata.bank_account.hexagone.domain.CompteCourant;
import kata.bank_account.hexagone.domain.LivretEpargne;

public class CompteBancaireApiMapper {

  public static CompteBancaireApi toCompteBancaireApi(CompteBancaire compte) {
    if (compte instanceof CompteCourant){
      return new CompteBancaireApi(
          compte.recupererNumeroDeCompte(),
          compte.recupererSolde(),
          "Courant"
      );
    }else if (compte instanceof LivretEpargne){
      return new CompteBancaireApi(
          compte.recupererNumeroDeCompte(),
          compte.recupererSolde(),
          "Epargne"
      );
    }else {
      throw new IllegalArgumentException("Type de compte inconnu");
    }

  }
}
