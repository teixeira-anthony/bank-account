package kata.bank_account.userSide;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import kata.bank_account.hexagone.domain.CompteCourant;
import kata.bank_account.hexagone.domain.LivretEpargne;
import kata.bank_account.hexagone.domain.port.CompteBancairePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CompteBancaireControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CompteBancairePort compteBancairePort;

  @BeforeEach
  void setUp() {
    CompteCourant compteCourant = new CompteCourant("12345");
    compteBancairePort.sauvegarderCompte(compteCourant);
    var livretEpargne = new LivretEpargne("livretEpargne12345");
    livretEpargne.definirPlafondDeDepot(new BigDecimal("500.00"));
    compteBancairePort.sauvegarderCompte(livretEpargne);
  }

  @Test
  void lorsqueLonDepose100SurUnCompteCourant_alorsLeCompteEstCrediteDe100() throws Exception {
    // Given
    CompteBancaireRequest request = new CompteBancaireRequest("12345", new BigDecimal("150.00"));

    // When
    mockMvc.perform(patch("/comptes/depot")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());

    // Then
    var compteCourantApresDepot = compteBancairePort.recupererCompte("12345");
    assertThat(compteCourantApresDepot.recupererSolde()).isEqualByComparingTo("150.00");
  }

  @Test
  void lorsqueLonRetire50SurUnCompteCourantEnPossedant200_alorsLeSoldeDuCompteEstDe150() throws Exception {
    // Given
    var compteCourantInitial = compteBancairePort.recupererCompte("12345");
    compteCourantInitial.deposer(new BigDecimal("200.00"));
    compteBancairePort.sauvegarderCompte(compteCourantInitial);

    CompteBancaireRequest request = new CompteBancaireRequest("12345", new BigDecimal("50.00"));

    // When
    mockMvc.perform(patch("/comptes/retrait")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());

    // Then
    var compteApresRetrait = compteBancairePort.recupererCompte("12345");
    assertThat(compteApresRetrait.recupererSolde()).isEqualByComparingTo("150.00");
  }

  @Test
  void lorsqueLonDepose200SurUnLivretEpargneDontLePlafondEstDe500_alorsLeCompteEstCrediteDe200() throws Exception {
    // Given
    CompteBancaireRequest request = new CompteBancaireRequest("livretEpargne12345", new BigDecimal("200.00"));

    // When
    mockMvc.perform(patch("/comptes/depot")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());

    // Then
    var compteCourantApresDepot = compteBancairePort.recupererCompte("livretEpargne12345");
    assertThat(compteCourantApresDepot.recupererSolde()).isEqualByComparingTo("200.00");
  }
}
