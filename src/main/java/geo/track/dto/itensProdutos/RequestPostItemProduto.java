package geo.track.dto.itensProdutos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record RequestPostItemProduto(
        @NotNull
        Integer fkOrdemServico,
        @NotNull
        Integer fkProduto,
        @NotNull
        @Positive
        Integer quantidade,
        @NotNull
        @PositiveOrZero
        Double precoProduto
) {
}
