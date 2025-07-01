package kata.bank_account.hexagone.domain.exceptions;

public class MontantDepotMaximumAtteintException extends RuntimeException {
  public MontantDepotMaximumAtteintException(String message) {
    super(message);
  }
}
