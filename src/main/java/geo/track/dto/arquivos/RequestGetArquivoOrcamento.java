package geo.track.dto.arquivos;

import jakarta.validation.constraints.NotNull;

public record RequestGetArquivoOrcamento(
        @NotNull
        Integer idOrcamento
) {
}
