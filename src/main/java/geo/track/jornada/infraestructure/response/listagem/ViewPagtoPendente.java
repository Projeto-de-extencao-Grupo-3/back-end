package geo.track.jornada.infraestructure.response.listagem;

import java.math.BigDecimal;

public record ViewPagtoPendente(
        BigDecimal totalValorNaoPago,
        BigDecimal quantidadeServicosNaoPagos
) {
    /**
     * Converte BigDecimal para Double (para valor)
     */
    public Double getTotalValorNaoPagoAsDouble() {
        return totalValorNaoPago != null ? totalValorNaoPago.doubleValue() : 0.0;
    }

    /**
     * Converte BigDecimal para Long (para quantidade)
     */
    public Long getQuantidadeServicosNaoPagosAsLong() {
        return quantidadeServicosNaoPagos != null ? quantidadeServicosNaoPagos.longValue() : 0L;
    }
}
