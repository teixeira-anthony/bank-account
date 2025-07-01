package kata.bank_account.hexagone.domain.port;

import java.math.BigDecimal;
import kata.bank_account.hexagone.domain.CompteBancaire;

public interface CompteBancairePort {
  void sauvegarderCompte(CompteBancaire compte);
  CompteBancaire recupererCompte(String numeroCompte);
  BigDecimal recupererSolde(String numeroCompte);
}
