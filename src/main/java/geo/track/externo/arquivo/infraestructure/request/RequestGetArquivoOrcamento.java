package geo.track.externo.arquivo.infraestructure.request;

import jakarta.validation.constraints.NotNull;

public record RequestGetArquivoOrcamento(
        @NotNull
        Integer idOrcamento
) {
}
