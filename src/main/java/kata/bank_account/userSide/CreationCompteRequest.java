package kata.bank_account.userSide;

import jakarta.validation.constraints.NotNull;
import kata.bank_account.hexagone.domain.TypeDeCompteEnum;

public record CreationCompteRequest(
    @NotNull
    TypeDeCompteEnum type
) {
}
