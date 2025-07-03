package kata.bank_account.hexagone;

import static kata.bank_account.hexagone.builders.CompteCourantBuilder.unCompte;
import static kata.bank_account.hexagone.builders.LivretEpargneBuilder.unLivret;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import kata.bank_account.hexagone.domain.CompteCourant;
import kata.bank_account.hexagone.domain.LivretEpargne;
import kata.bank_account.hexagone.domain.exceptions.MontantDepotInvalideException;
import kata.bank_account.hexagone.domain.exceptions.MontantDepotMaximumAtteintException;
import kata.bank_account.hexagone.domain.port.CompteBancairePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class DeposerArgentTest {

  private CompteBancairePort compteBancairePort;
  private DeposerArgent deposerArgent;

  @BeforeEach
  void setUp() {
    compteBancairePort = mock(CompteBancairePort.class);
    deposerArgent = new DeposerArgent(compteBancairePort);
  }

  @Nested
  class CompteCourantTest {

    @Test
    void lorsqueJeDepose100eurosSurUnNouveauCompteCourant_alorsLeCompteCourantSauvegardeContient100euros() {
      // Given
      var numeroDeCompte = "compte123456";
      CompteCourant compte = unCompte()
          .avecNumeroCompte(numeroDeCompte)
          .avecSolde(new BigDecimal("0"))
          .build();
      var montant = new BigDecimal("100.00");
      when(compteBancairePort.recupererCompte(numeroDeCompte)).thenReturn(compte);

      // When
      var solde = deposerArgent.execute(numeroDeCompte, montant);

      // Then
      ArgumentCaptor<CompteCourant> captor = ArgumentCaptor.forClass(CompteCourant.class);
      verify(compteBancairePort).sauvegarderCompte(captor.capture());
      CompteCourant compteSauvegarde = captor.getValue();
      assertEquals(numeroDeCompte, compteSauvegarde.recupererNumeroDeCompte());
      assertEquals(new BigDecimal("100.00"), solde);
    }

    @Test
    void lorsqueLonSouhaiteDeposerUnMontantInferieurOuEgalA0SurUnCompteCourant_alorsRetourneMontantDepotInvalideException() {
      // Given
      var numeroDeCompte = "compte123456";
      CompteCourant compte = unCompte()
          .avecNumeroCompte(numeroDeCompte)
          .avecSolde(new BigDecimal("0"))
          .build();

      // When Then
      assertThrows(MontantDepotInvalideException.class, () -> compte.deposer(BigDecimal.ZERO));
      assertThrows(MontantDepotInvalideException.class, () -> compte.deposer(new BigDecimal("-50")));
    }
  }

  @Nested
  class LivretEpargneTest {

    @Test
    void lorsqueLonSouhaiteDeposer2000SurUnLivretADontLePlafondEstDe2500_alorsLeSoldeEstDe2000(){
      // Given
      var numeroDeCompte = "compte123456";
      var livret = unLivret()
          .avecNumeroCompte(numeroDeCompte)
          .avecPlafondDeDepot(new BigDecimal("2500"))
          .build();
      var montantADeposer = new BigDecimal("2000.00");
      when(compteBancairePort.recupererCompte(numeroDeCompte)).thenReturn(livret);

      // When
      var solde = deposerArgent.execute(numeroDeCompte, montantADeposer);

      // Then
      ArgumentCaptor<LivretEpargne> captor = ArgumentCaptor.forClass(LivretEpargne.class);
      verify(compteBancairePort).sauvegarderCompte(captor.capture());
      var compteSauvegarde = captor.getValue();
      assertEquals(numeroDeCompte, compteSauvegarde.recupererNumeroDeCompte());
      assertEquals(new BigDecimal("2000.00"), solde);
    }

    @Test
    void lorsqueLonSouhaiteDeposerUnMontantSuperieurAuPlafondDeDepotSurUnLivretEpargne_alorsRetourneMontantDepotMaximumAtteintException() {
      // Given
      var numeroDeCompte = "compte123456";
      var livret = unLivret()
          .avecNumeroCompte(numeroDeCompte)
          .avecPlafondDeDepot(new BigDecimal("1000.00"))
          .build();
      var montantADeposer = new BigDecimal("2000.00");

      // When Then
      assertThrows(MontantDepotMaximumAtteintException.class, () -> livret.deposer(montantADeposer)); //TODO changer l'exception avec plus de sens métier
    }

    @Test
    void lorsqueLonSouhaiteDeposerUnMontantInferieurOuEgalA0SurUnLivret_alorsRetourneMontantDepotInvalideException() {
      // Given
      var numeroDeCompte = "compte123456";
      LivretEpargne compte = unLivret()
          .avecNumeroCompte(numeroDeCompte)
          .avecSolde(new BigDecimal("0"))
          .avecPlafondDeDepot(new BigDecimal("2500"))
          .build();

      // When Then
      assertThrows(MontantDepotInvalideException.class, () -> compte.deposer(BigDecimal.ZERO));
      assertThrows(MontantDepotInvalideException.class, () -> compte.deposer(new BigDecimal("-50")));
    }
  }
}
