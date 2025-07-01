package kata.bank_account.userSide;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Compte bancaire", description = "Opérations sur les comptes bancaires")
public interface CompteBancaireControllerSwagger {

  @Operation(summary = "Créer un compte bancaire", description = "Crée un compte bancaire du type spécifié")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Compte créé avec succès"),
      @ApiResponse(responseCode = "400", description = "Type de compte manquant ou invalide")
  })
  ResponseEntity<CreationCompteResponse> creerCompte(@Valid @RequestBody CreationCompteRequest request);


  @Operation(summary = "Déposer de l'argent", description = "Dépose un montant sur un compte bancaire existant")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Dépôt effectué avec succès"),
      @ApiResponse(responseCode = "400", description = "Requête invalide (montant ou numéro de compte incorrect)")
  })
  ResponseEntity<Void> deposerArgent(@Valid @RequestBody CompteBancaireRequest request);

  @Operation(summary = "Retirer de l'argent", description = "Retire un montant d’un compte bancaire existant")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Retrait effectué avec succès"),
      @ApiResponse(responseCode = "400", description = "Requête invalide ou solde insuffisant")
  })
  ResponseEntity<Void> retirerArgent(@Valid @RequestBody CompteBancaireRequest request);

  @Operation(
      summary = "Récupérer le solde d’un compte bancaire",
      description = "Renvoie le solde actuel du compte identifié par son numéro.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Solde récupéré avec succès",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))),
          @ApiResponse(responseCode = "404", description = "Compte introuvable")
      }
  )
  public ResponseEntity<BigDecimal> getSolde(
      @Parameter(description = "Numéro du compte", required = true)
      @PathVariable String numeroCompte);

}
