package kata.bank_account.hexagone.domain.exceptions;

public class SoldeInsuffisantException extends RuntimeException {
  public SoldeInsuffisantException(String message) {
    super(message);
  }
}
