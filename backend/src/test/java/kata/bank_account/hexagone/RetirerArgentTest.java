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
import kata.bank_account.hexagone.domain.exceptions.SoldeInsuffisantException;
import kata.bank_account.hexagone.domain.port.CompteBancairePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class RetirerArgentTest {

  private CompteBancairePort compteBancairePort;
  private RetirerArgent retirerArgent;

  @BeforeEach
  void setUp() {
    compteBancairePort = mock(CompteBancairePort.class);
    retirerArgent = new RetirerArgent(compteBancairePort);
  }

  @Nested
  class CompteCourantTest {

    @Test
    void lorsqueJeRetire100SurUnCompteCourantEnPossedant150_alorsLeCompteCourantNeContientPlusQue50() {
      // Given
      var numeroDeCompte = "compte1234";
      CompteCourant compte = unCompte()
          .avecNumeroCompte(numeroDeCompte)
          .avecSolde(new BigDecimal("150.00"))
          .build();
      var montantARetirer = new BigDecimal("100.00");
      when(compteBancairePort.recupererCompte(numeroDeCompte)).thenReturn(compte);
      // When
      var solde = retirerArgent.execute(numeroDeCompte, montantARetirer);
      // Then
      ArgumentCaptor<CompteCourant> captor = ArgumentCaptor.forClass(CompteCourant.class);
      verify(compteBancairePort).sauvegarderCompte(captor.capture());
      CompteCourant compteSauvegarde = captor.getValue();
      assertEquals(numeroDeCompte, compteSauvegarde.recupererNumeroDeCompte());
      assertEquals(new BigDecimal("50.00"), solde);
    }

    @Test
    void lorsqueJeRetire100SurUnCompteCourantNePossedantQue50_alorsRetourneSoldeInsuffisantException() {
      // Given
      var numeroDeCompte = "compte1234";
      CompteCourant compte = unCompte()
          .avecNumeroCompte(numeroDeCompte)
          .avecSolde(new BigDecimal("50.00"))
          .build();
      var montantARetirer = new BigDecimal("100.00");
      when(compteBancairePort.recupererCompte(numeroDeCompte)).thenReturn(compte);

      // When Then
      assertThrows(SoldeInsuffisantException.class, () -> compte.retirer(montantARetirer));
    }

    @Test
    void lorsqueJeRetire100SurUnCompteCourantA50MaisQueLeCompteAUneAutorisationDeDecouvertDe50_alorsLeSoldeDuCompteEstAmoins50() {
      // Given
      var numeroDeCompte = "compte1234";
      CompteCourant compte = unCompte()
          .avecNumeroCompte(numeroDeCompte)
          .avecSolde(new BigDecimal("50.00"))
          .avecAutorisationDecouvert(new BigDecimal("50.00"))
          .build();
      var montantARetirer = new BigDecimal("100.00");
      when(compteBancairePort.recupererCompte(numeroDeCompte)).thenReturn(compte);

      // When
      var solde = retirerArgent.execute(numeroDeCompte, montantARetirer);

      //Then
      ArgumentCaptor<CompteCourant> captor = ArgumentCaptor.forClass(CompteCourant.class);
      verify(compteBancairePort).sauvegarderCompte(captor.capture());
      assertEquals(new BigDecimal("-50.00"), solde);
    }
  }

  @Nested
  class LivretEpargneTest {
    @Test
    void lorsqueJeRetire100SurUnLivretEpargneEnPossedant150_alorsLeLivretNeContientPlusQue50() {
      // Given
      var numeroDeCompte = "compte1234";
      LivretEpargne compte = unLivret()
          .avecNumeroCompte(numeroDeCompte)
          .avecSolde(new BigDecimal("150.00"))
          .avecPlafondDeDepot(new BigDecimal("150.00"))
          .build();
      var montantARetirer = new BigDecimal("100.00");
      when(compteBancairePort.recupererCompte(numeroDeCompte)).thenReturn(compte);
      // When
      var solde = retirerArgent.execute(numeroDeCompte, montantARetirer);
      // Then
      ArgumentCaptor<LivretEpargne> captor = ArgumentCaptor.forClass(LivretEpargne.class);
      verify(compteBancairePort).sauvegarderCompte(captor.capture());
      LivretEpargne compteSauvegarde = captor.getValue();
      assertEquals(numeroDeCompte, compteSauvegarde.recupererNumeroDeCompte());
      assertEquals(new BigDecimal("50.00"), solde);
    }

    @Test
    void lorsqueJeRetire100SurUnLivetEpargneNePossedantQue50_alorsRetourneSoldeInsuffisantException() {
      // Given
      var numeroDeCompte = "compte1234";
      LivretEpargne compte = unLivret()
          .avecNumeroCompte(numeroDeCompte)
          .avecSolde(new BigDecimal("50.00"))
          .avecPlafondDeDepot(new BigDecimal("500.00"))
          .build();
      var montantARetirer = new BigDecimal("100.00");
      when(compteBancairePort.recupererCompte(numeroDeCompte)).thenReturn(compte);

      // When Then
      assertThrows(SoldeInsuffisantException.class, () -> compte.retirer(montantARetirer));
    }

    @Test
    void lorsqueJeRetireUnMontantNegatifSurUnLivretEpargne_alorsRetourneIllegalArgumentException() {
      // Given
      var numeroDeCompte = "compte1234";
      LivretEpargne compte = unLivret()
          .avecNumeroCompte(numeroDeCompte)
          .avecSolde(new BigDecimal("50.00"))
          .avecPlafondDeDepot(new BigDecimal("500.00"))
          .build();
      var montantARetirer = new BigDecimal("-100.00");
      when(compteBancairePort.recupererCompte(numeroDeCompte)).thenReturn(compte);

      // When Then
      assertThrows(IllegalArgumentException.class, () -> compte.retirer(montantARetirer));
    }
  }
}