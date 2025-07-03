package kata.bank_account.serverSide;

import java.util.List;
import kata.bank_account.hexagone.domain.CompteBancaire;
import kata.bank_account.hexagone.domain.CompteCourant;
import kata.bank_account.hexagone.domain.LivretEpargne;
import kata.bank_account.hexagone.domain.port.CompteBancairePort;
import kata.bank_account.serverSide.entity.*;
import kata.bank_account.serverSide.repository.CompteBancaireJpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class CompteBancairePostgresAdapter implements CompteBancairePort {

  private final CompteBancaireJpaRepository repository;

  public CompteBancairePostgresAdapter(CompteBancaireJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public void sauvegarderCompte(CompteBancaire compte) {
    CompteBancaireEntity entity = toEntity(compte);
    repository.save(entity);
  }

  @Override
  public CompteBancaire recupererCompte(String numeroCompte) {
    var entity = repository.findById(numeroCompte)
        .orElseThrow(() -> new IllegalArgumentException("Aucun compte trouvé avec le numéro : " + numeroCompte));
    return toDomaine(entity);
  }

  @Override
  public BigDecimal recupererSolde(String numeroCompte) {
    return recupererCompte(numeroCompte).recupererSolde();
  }

  @Override
  public List<CompteBancaire> recupererComptes() {
    return repository.findAll().stream().map(this::toDomaine).toList();
  }

  // ======= MAPPINGS =======

  private CompteBancaireEntity toEntity(CompteBancaire compte) {
    if (compte instanceof CompteCourant cc) {
      var entity = new CompteCourantEntity();
      entity.setNumeroDeCompte(cc.recupererNumeroDeCompte());
      entity.setSolde(cc.recupererSolde());
      entity.setAutorisationDecouvert(cc.recupererAutorisationDecouvert());
      return entity;
    } else if (compte instanceof LivretEpargne le) {
      var entity = new LivretEpargneEntity();
      entity.setNumeroDeCompte(le.recupererNumeroDeCompte());
      entity.setSolde(le.recupererSolde());
      entity.setPlafondDepot(le.getPlafondDepot());
      return entity;
    } else {
      throw new IllegalArgumentException("Type de compte non supporté : " + compte.getClass().getSimpleName());
    }
  }

  private CompteBancaire toDomaine(CompteBancaireEntity entity) {
    if (entity instanceof CompteCourantEntity cc) {
      return new CompteCourant(
          cc.getNumeroDeCompte(),
          cc.getSolde(),
          cc.getAutorisationDecouvert()
      );
    } else if (entity instanceof LivretEpargneEntity le) {
      return new LivretEpargne(
          le.getNumeroDeCompte(),
          le.getSolde(),
          le.getPlafondDepot()
      );
    } else {
      throw new IllegalArgumentException("Type d'entité non supporté : " + entity.getClass().getSimpleName());
    }
  }
}
