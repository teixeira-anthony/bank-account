package kata.bank_account.userSide;

import kata.bank_account.hexagone.domain.exceptions.MontantDepotInvalideException;
import kata.bank_account.hexagone.domain.exceptions.MontantDepotMaximumAtteintException;
import kata.bank_account.hexagone.domain.exceptions.SoldeInsuffisantException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

  @RestControllerAdvice
  public class CompteBancaireExceptionHandler {

    @ExceptionHandler(SoldeInsuffisantException.class)
    public ResponseEntity<String> handleSoldeInsuffisant(SoldeInsuffisantException ex) {
      return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MontantDepotInvalideException.class)
    public ResponseEntity<String> handleMontantDepotInvalide(MontantDepotInvalideException ex) {
      return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(MontantDepotMaximumAtteintException.class)
    public ResponseEntity<String> handleMontantDepotMaximumAtteint(MontantDepotMaximumAtteintException ex) {
      return ResponseEntity.badRequest().body(ex.getMessage());
    }

  }
