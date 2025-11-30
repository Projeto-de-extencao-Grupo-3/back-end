package geo.track.dto.itensProdutos;

import jakarta.validation.constraints.NotNull;

public record RequestPutItemProduto(
        @NotNull
        Integer quantidade,
        Double precoProduto,
        Boolean baixado
) {
}
