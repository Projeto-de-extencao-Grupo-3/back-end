package geo.track.jornada.infraestructure.response.listagem;

import java.math.BigDecimal;

public record ViewPagtoRealizado(
        BigDecimal totalPagamentosRealizados
) {
    /**
     * Converte BigDecimal para Long
     */
    public Long getQuantidadePagamentosRealizadosAsLong() {
        return totalPagamentosRealizados != null ? totalPagamentosRealizados.longValue() : 0L;
    }
}
