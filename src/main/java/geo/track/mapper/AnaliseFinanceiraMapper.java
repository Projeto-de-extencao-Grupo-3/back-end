package geo.track.mapper;

import geo.track.jornada.entity.OrdemDeServico;
import geo.track.dto.analise.financeira.response.ResponseNotaFiscals;
import geo.track.dto.analise.financeira.response.ResponsePagamentos;

import java.util.List;

public class AnaliseFinanceiraMapper {
    public static ResponseNotaFiscals toResponseNotaFiscalPendente(Long quantidadeNotasFiscaisPendentes, List<OrdemDeServico> ordensPendentes) {
        if (quantidadeNotasFiscaisPendentes == null) {
            quantidadeNotasFiscaisPendentes = 0L;
        }

        return new ResponseNotaFiscals(quantidadeNotasFiscaisPendentes, OrdemDeServicoMapper.toCard(ordensPendentes));
    }

    public static ResponsePagamentos toResponsePagamentoRealizado(Long totalPagamentosRealizados, List<OrdemDeServico> ordensNotaFiscalpendente) {
        if (totalPagamentosRealizados == null) {
            totalPagamentosRealizados = 0L;
        }

        return new ResponsePagamentos(null, null, totalPagamentosRealizados, OrdemDeServicoMapper.toCard(ordensNotaFiscalpendente));
    }

    public static ResponsePagamentos toResponsePagamentoPendente(Double totalValorPendente, Long quantidadePagamentosPendente, List<OrdemDeServico> ordensNotaFiscalpendente) {
        if (totalValorPendente == null) {
            totalValorPendente = 0.0;
        }
        if (quantidadePagamentosPendente == null) {
            quantidadePagamentosPendente = 0L;
        }
        return new ResponsePagamentos(totalValorPendente, quantidadePagamentosPendente, null, OrdemDeServicoMapper.toCard(ordensNotaFiscalpendente));
    }
}
