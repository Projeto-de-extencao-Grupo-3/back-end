package geo.track.jornada.infraestructure.response.listagem;

import java.math.BigDecimal;

public record ViewNotaFiscal(
        BigDecimal quantidadeNfsPendentes
) {
    /**
     * Converte BigDecimal para Long
     */
    public Long getQuantidadeNfsPendentesAsLong() {
        return quantidadeNfsPendentes != null ? quantidadeNfsPendentes.longValue() : 0L;
    }
}
