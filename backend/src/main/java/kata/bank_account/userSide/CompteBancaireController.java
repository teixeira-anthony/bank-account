package kata.bank_account.userSide;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import kata.bank_account.hexagone.CreerCompte;
import kata.bank_account.hexagone.DeposerArgent;
import kata.bank_account.hexagone.RetirerArgent;
import kata.bank_account.hexagone.domain.port.CompteBancairePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comptes")
public class CompteBancaireController implements CompteBancaireControllerSwagger {

  private final DeposerArgent deposerArgent;
  private final RetirerArgent retirerArgent;
  private final CreerCompte creerCompte;
  private final CompteBancairePort compteBancairePort;

  public CompteBancaireController(DeposerArgent deposerArgent, RetirerArgent retirerArgent,
      CreerCompte creerCompte, CompteBancairePort compteBancairePort) {
    this.deposerArgent = deposerArgent;
    this.retirerArgent = retirerArgent;
    this.creerCompte = creerCompte;
    this.compteBancairePort = compteBancairePort;
  }

  @Override
  @PostMapping("/creerCompte")
  public ResponseEntity<CreationCompteResponse> creerCompte(CreationCompteRequest request) {
    String numeroCompteCree = creerCompte.executer(request.type());
    return ResponseEntity.status(201).body(new CreationCompteResponse(numeroCompteCree));
  }

  @Override
  @PatchMapping("/depot")
  public ResponseEntity<Void> deposerArgent(@Valid @RequestBody CompteBancaireRequest request) {
    deposerArgent.execute(request.numeroCompte(), request.montant());
    return ResponseEntity.ok().build();
  }

  @Override
  @PatchMapping("/retrait")
  public ResponseEntity<Void> retirerArgent(@Valid @RequestBody CompteBancaireRequest request) {
    retirerArgent.execute(request.numeroCompte(), request.montant());
    return ResponseEntity.ok().build();
  }

  @Override
  @GetMapping("/{numeroCompte}/solde")
  public ResponseEntity<BigDecimal> getSolde(
      @Parameter(description = "Numéro du compte", required = true)
      @PathVariable String numeroCompte
  ) {
    BigDecimal solde = compteBancairePort.recupererSolde(numeroCompte);
    return ResponseEntity.ok(solde);
  }
}
