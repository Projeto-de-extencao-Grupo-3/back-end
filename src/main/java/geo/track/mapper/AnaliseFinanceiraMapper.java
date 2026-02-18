package geo.track.mapper;

import geo.track.dto.analise.financeira.response.ResponseNotaFiscals;
import geo.track.dto.analise.financeira.response.ResponsePagamentos;
import geo.track.dto.os.response.OrdemDeServicoResponse;

import java.util.List;

public class AnaliseFinanceiraMapper {
    public static ResponseNotaFiscals toResponseNotaFiscalPendente(Long quantidadeNotasFiscaisPendentes, List<OrdemDeServicoResponse> ordensPendentes) {
        if (quantidadeNotasFiscaisPendentes == null) {
            quantidadeNotasFiscaisPendentes = 0L;
        }

        return new ResponseNotaFiscals(quantidadeNotasFiscaisPendentes, ordensPendentes);
    }

    public static ResponsePagamentos toResponsePagamentoRealizado(Long totalPagamentosRealizados, List<OrdemDeServicoResponse> ordensNotaFiscalpendente) {
        if (totalPagamentosRealizados == null) {
            totalPagamentosRealizados = 0L;
        }
        return new ResponsePagamentos(null, null, totalPagamentosRealizados, ordensNotaFiscalpendente);
    }

    public static ResponsePagamentos toResponsePagamentoPendente(Double totalValorPendente, Long totalPagamentosPendentes, List<OrdemDeServicoResponse> ordensNotaFiscalpendente) {
        if (totalValorPendente == null) {
            totalValorPendente = 0.0;
        }
        if (totalPagamentosPendentes == null) {
            totalPagamentosPendentes = 0L;
        }
        return new ResponsePagamentos(totalValorPendente, totalPagamentosPendentes, null, ordensNotaFiscalpendente);
    }
}
