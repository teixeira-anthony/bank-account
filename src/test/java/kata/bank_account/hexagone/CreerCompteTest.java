package kata.bank_account.hexagone;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import kata.bank_account.hexagone.domain.CompteCourant;
import kata.bank_account.hexagone.domain.LivretEpargne;
import kata.bank_account.hexagone.domain.TypeDeCompteEnum;
import kata.bank_account.hexagone.domain.port.CompteBancairePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class CreerCompteTest {

  private CompteBancairePort compteBancairePort;
  private CreerCompte creerCompte;

  @BeforeEach
  void setUp() {
    compteBancairePort = mock(CompteBancairePort.class);
    creerCompte = new CreerCompte(compteBancairePort);
  }

  @Test
  void lorsqueLonCreerUnCompteCourant_alorsLeCompteEstCreerEtSauvegarde(){
    // Given
    var typeDeCompte = TypeDeCompteEnum.COURANT;
    // When
    creerCompte.executer(typeDeCompte);
    // Then
    ArgumentCaptor<CompteCourant> captor = ArgumentCaptor.forClass(CompteCourant.class);
    verify(compteBancairePort).sauvegarderCompte(captor.capture());

  }
  @Test
  void lorsqueLonCreerUnCompteEpargne_alorsLeCompteEstCreerEtSauvegarde(){
    // Given
    var typeDeCompte = TypeDeCompteEnum.EPARGNE;
    // When
    creerCompte.executer(typeDeCompte);
    // Then
    ArgumentCaptor<LivretEpargne> captor = ArgumentCaptor.forClass(LivretEpargne.class);
    verify(compteBancairePort).sauvegarderCompte(captor.capture());

  }
}