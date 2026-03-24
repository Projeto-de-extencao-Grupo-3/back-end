package geo.track.external.request.arquivo;

import jakarta.validation.constraints.NotNull;

public record RequestGetArquivoOrcamento(
        @NotNull
        Integer idOrcamento
) {
}
