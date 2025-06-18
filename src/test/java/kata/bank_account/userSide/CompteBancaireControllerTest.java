package kata.bank_account.userSide;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import kata.bank_account.hexagone.CreerCompte;
import kata.bank_account.hexagone.DeposerArgent;
import kata.bank_account.hexagone.RetirerArgent;
import kata.bank_account.hexagone.domain.TypeDeCompteEnum;
import kata.bank_account.hexagone.domain.exceptions.MontantDepotInvalideException;
import kata.bank_account.hexagone.domain.exceptions.MontantDepotMaximumAtteintException;
import kata.bank_account.hexagone.domain.exceptions.SoldeInsuffisantException;
import kata.bank_account.serverSide.CompteBancaireEnMemoire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class CompteBancaireControllerTest {

  private MockMvc mockMvc;

  private DeposerArgent deposerArgent;
  private RetirerArgent retirerArgent;
  private CreerCompte creerCompte;
  private CompteBancaireEnMemoire compteBancaireRepo;

  private ObjectMapper objectMapper = new ObjectMapper();

  private CompteBancaireController controller;

  @BeforeEach
  void setup() {
    deposerArgent = mock(DeposerArgent.class);
    retirerArgent = mock(RetirerArgent.class);
    creerCompte = mock(CreerCompte.class);
    compteBancaireRepo = mock(CompteBancaireEnMemoire.class);

    controller = new CompteBancaireController(deposerArgent, retirerArgent, creerCompte, compteBancaireRepo);

    mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setControllerAdvice(new CompteBancaireExceptionHandler())
        .build();
  }

  @Test
  void lorsqueLonVeutCreerUnCompteBancaire_alorsUnCompteEstCreerEtSonIdRenvoyer() throws Exception {
    // Given
    var request = new CreationCompteRequest(TypeDeCompteEnum.COURANT);
    String numeroCompteGenere = "CompteCourant-1234";
    when(creerCompte.executer(eq(TypeDeCompteEnum.COURANT)))
        .thenReturn(numeroCompteGenere);

    // When Then
    mockMvc.perform(post("/comptes/creerCompte")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.numeroCompte").value(numeroCompteGenere));
  }
  @Test
  void lorsqueLonVeutRetirerUnMontantSuperieurAuSolde_alorsRetourneErreur400ExceptionSoldeInsuffisant() throws Exception {
    // Given
    CompteBancaireRequest request = new CompteBancaireRequest("12345", new BigDecimal("100.00"));
    doThrow(new SoldeInsuffisantException("Solde insuffisant pour retirer 100.00€"))
        .when(retirerArgent).execute("12345", new BigDecimal("100.00"));

    // When Then
    mockMvc.perform(patch("/comptes/retrait")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }
  @Test
  void lorsqueLonVeutDeposerUnMontantNegatif_alorsRetourneErreur400MontantDepotInvalideException() throws Exception {
    // Given
    CompteBancaireRequest request = new CompteBancaireRequest("12345", new BigDecimal("-100.00"));
    doThrow(new MontantDepotInvalideException("Le montant doit être positif"))
        .when(deposerArgent).execute("12345", new BigDecimal("-100.00"));

    // When Then
    mockMvc.perform(patch("/comptes/depot")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void lorsqueLonVeutDeposerUnMontantSurUnLivretEpargneSuperieurAuPlafondDeDepot_alorsRetourneErreur400MontantDepotMaximumAtteintException() throws Exception {
    // Given
    CompteBancaireRequest request = new CompteBancaireRequest("12345", new BigDecimal("-100.00"));
    doThrow(new MontantDepotMaximumAtteintException("Le montant doit être positif"))
        .when(deposerArgent).execute("12345", new BigDecimal("-100.00"));

    // When Then
    mockMvc.perform(patch("/comptes/depot")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }
}