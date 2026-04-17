package geo.track.catalogo.item_produto.infraestructure.request;

import jakarta.validation.constraints.NotNull;

public record RequestPutItemProduto(
        @NotNull
        Integer quantidade,
        @NotNull
        Double precoProduto,
        @NotNull
        Boolean baixado
) { }
