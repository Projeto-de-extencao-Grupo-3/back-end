package geo.track.catalogo.item_produto.infraestructure.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record RequestPostItemProduto (
        @NotNull
        Integer fkProduto,
        @NotNull
        @Positive
        Integer quantidade,
        @NotNull
        @PositiveOrZero
        Double precoProduto
)  {
}
