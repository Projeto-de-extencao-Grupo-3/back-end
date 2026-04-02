package geo.track.dto.itensProdutos;

import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import jakarta.validation.constraints.NotNull;

public record RequestPutItemProduto(
        @NotNull
        Integer quantidade,
        @NotNull
        Double precoProduto,
        @NotNull
        Boolean baixado
) { }
