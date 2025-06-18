package kata.bank_account.config;

import kata.bank_account.hexagone.CreerCompte;
import kata.bank_account.hexagone.DeposerArgent;
import kata.bank_account.hexagone.RetirerArgent;
import kata.bank_account.hexagone.domain.port.CompteBancairePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

  @Bean
  public DeposerArgent deposerArgent(CompteBancairePort compteBancairePort) {
    return new DeposerArgent(compteBancairePort);
  }

  @Bean
  public RetirerArgent retirerArgent(CompteBancairePort compteBancairePort) {
    return new RetirerArgent(compteBancairePort);
  }

  @Bean
  public CreerCompte creerCompte(CompteBancairePort compteBancairePort) {
    return new CreerCompte(compteBancairePort);
  }
}
