package kata.bank_account.userSide;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CompteBancaireRequest(
    @NotBlank
    String numeroCompte,

    @NotNull
    @DecimalMin("0.01")
    BigDecimal montant
) {}
