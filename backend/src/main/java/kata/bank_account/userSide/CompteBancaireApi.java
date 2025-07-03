package kata.bank_account.userSide;

import java.math.BigDecimal;

public record CompteBancaireApi(String numeroDeCompte, BigDecimal solde, String typeDeCompte) {}

