package kata.bank_account.serverSide;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import kata.bank_account.hexagone.domain.CompteBancaire;
import kata.bank_account.hexagone.domain.port.CompteBancairePort;
import org.springframework.stereotype.Repository;

@Repository
public class CompteBancaireEnMemoire implements CompteBancairePort {
  private final Map<String, CompteBancaire> comptes = new HashMap<>();

  @Override
  public void sauvegarderCompte(CompteBancaire compte) {
    comptes.put(compte.recupererNumeroDeCompte(), compte);
  }

  @Override
  public CompteBancaire recupererCompte(String numeroCompte) {
    var compte = comptes.get(numeroCompte);
    if (compte == null) {
      throw new IllegalArgumentException("Aucun compte trouvé avec le numéro : " + numeroCompte);
    }
    return compte;
  }

  @Override
  public BigDecimal recupererSolde(String numeroCompte) {
    var compte = comptes.get(numeroCompte);
    if (compte == null) {
      throw new IllegalArgumentException("Aucun compte trouvé avec le numéro : " + numeroCompte);
    }
    return compte.recupererSolde();
  }
}

