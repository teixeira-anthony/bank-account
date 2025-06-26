package kata.bank_account.serverSide.repository;

import kata.bank_account.serverSide.entity.CompteBancaireEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteBancaireJpaRepository extends JpaRepository<CompteBancaireEntity, String> {
}

