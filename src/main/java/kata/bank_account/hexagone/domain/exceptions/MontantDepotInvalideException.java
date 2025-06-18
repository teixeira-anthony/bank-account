package kata.bank_account.hexagone.domain.exceptions;

public class MontantDepotInvalideException extends RuntimeException {
  public MontantDepotInvalideException(String message) {
    super(message);
  }
}
