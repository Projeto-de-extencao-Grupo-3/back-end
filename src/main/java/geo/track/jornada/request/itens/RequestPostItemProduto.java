package geo.track.jornada.request.itens;

import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
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
