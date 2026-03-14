package geo.track.dto.itensProdutos;

import jakarta.validation.constraints.NotNull;

public record RequestPutItemProduto(
        @NotNull
        Integer quantidade,
        @NotNull
        Double precoProduto,
        @NotNull
        Boolean baixado
) {
}
