package geo.track.service;

import geo.track.domain.OrdemDeServico;
import geo.track.dto.analise.financeira.response.ResponseNotaFiscals;
import geo.track.dto.analise.financeira.response.ResponsePagamentos;
import geo.track.dto.os.response.ViewNotaFiscal;
import geo.track.mapper.AnaliseFinanceiraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnaliseFinanceiraService {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    public ResponsePagamentos findOrdensPagtoCondition(Integer idOficina, Boolean nfRealizada, Boolean pagtoRealizado) {
        List<OrdemDeServico> ordensPagtoPendente = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(nfRealizada, pagtoRealizado, idOficina);

        if (pagtoRealizado) {
            var kpi = ORDEM_SERVICO_SERVICE.exibirKpiPagtoRealizado(idOficina);
            return AnaliseFinanceiraMapper.toResponsePagamentoRealizado(
                    kpi.totalPagamentosRealizados(),
                    ordensPagtoPendente
            );
        } else {
            var kpi = ORDEM_SERVICO_SERVICE.exibirKpiPagtoPendente(idOficina);
            return AnaliseFinanceiraMapper.toResponsePagamentoPendente(
                    kpi.totalValorNaoPago(),
                    kpi.quantidadeServicosNaoPagos(),
                    ordensPagtoPendente
            );
        }
    }

    public ResponseNotaFiscals findOrdensNotaFiscalCondition(Integer idOficina, Boolean nfRealizada, Boolean pagtoRealizado) {
        ViewNotaFiscal kpiNotaFiscal = ORDEM_SERVICO_SERVICE.exibirKpiNotaFiscal(idOficina);
        List<OrdemDeServico> ordensNotaFiscalpendente = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(nfRealizada, pagtoRealizado, idOficina);
        return AnaliseFinanceiraMapper.toResponseNotaFiscalPendente(kpiNotaFiscal.quantidadeNfsPendentes(), ordensNotaFiscalpendente);

    }
}
