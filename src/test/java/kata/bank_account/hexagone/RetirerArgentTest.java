package kata.bank_account.hexagone;

import static kata.bank_account.hexagone.builders.CompteCourantBuilder.unCompte;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import kata.bank_account.hexagone.domain.CompteCourant;
import kata.bank_account.hexagone.domain.exceptions.SoldeInsuffisantException;
import kata.bank_account.hexagone.domain.port.CompteBancairePort;
import org.junit.jupiter.api.BeforeEach;
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
    retirerArgent.execute(numeroDeCompte, montantARetirer);
    // Then
    ArgumentCaptor<CompteCourant> captor = ArgumentCaptor.forClass(CompteCourant.class);
    verify(compteBancairePort).sauvegarderCompte(captor.capture());
    CompteCourant compteSauvegarde = captor.getValue();
    assertEquals(numeroDeCompte, compteSauvegarde.recupererNumeroDeCompte());
    assertEquals(new BigDecimal("50.00"), compteSauvegarde.recupererSolde());
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
  void lorsqueJeRetire100SurUnCompteCourantA50MaisQueLeCompteAUneAutorisationDeDecouvertDe50_alorsLeSoldeDuCompteEstAmoins50(){
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
    retirerArgent.execute(numeroDeCompte, montantARetirer);

    //Then
    ArgumentCaptor<CompteCourant> captor = ArgumentCaptor.forClass(CompteCourant.class);
    verify(compteBancairePort).sauvegarderCompte(captor.capture());
    CompteCourant compteSauvegarde = captor.getValue();
    assertEquals(new BigDecimal("-50.00"), compteSauvegarde.recupererSolde());
  }
}