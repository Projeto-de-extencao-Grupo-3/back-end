package geo.track.service;

import geo.track.domain.OrdemDeServico;
import geo.track.dto.analise.financeira.response.ResponseNotaFiscals;
import geo.track.dto.analise.financeira.response.ResponsePagamentos;
import geo.track.dto.os.response.ViewNotaFiscal;
import geo.track.log.Log;
import geo.track.mapper.AnaliseFinanceiraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnaliseFinanceiraService {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final Log log;

    public ResponsePagamentos findOrdensPagtoCondition(Integer idOficina, Boolean nfRealizada, Boolean pagtoRealizado) {
        log.info("Iniciando busca de ordens de serviço por condição de pagamento. Oficina ID: {}, NF Realizada: {}, Pagamento Realizado: {}", idOficina, nfRealizada, pagtoRealizado);
        List<OrdemDeServico> ordensPagtoPendente = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(nfRealizada, pagtoRealizado, idOficina);

        if (pagtoRealizado) {
            log.info("Calculando KPIs para pagamentos realizados da oficina ID: {}", idOficina);
            var kpi = ORDEM_SERVICO_SERVICE.exibirKpiPagtoRealizado(idOficina);
            return AnaliseFinanceiraMapper.toResponsePagamentoRealizado(
                    kpi.totalPagamentosRealizados(),
                    ordensPagtoPendente
            );
        } else {
            log.info("Calculando KPIs para pagamentos pendentes da oficina ID: {}", idOficina);
            var kpi = ORDEM_SERVICO_SERVICE.exibirKpiPagtoPendente(idOficina);
            return AnaliseFinanceiraMapper.toResponsePagamentoPendente(
                    kpi.totalValorNaoPago(),
                    kpi.quantidadeServicosNaoPagos(),
                    ordensPagtoPendente
            );
        }
    }

    public ResponseNotaFiscals findOrdensNotaFiscalCondition(Integer idOficina, Boolean nfRealizada, Boolean pagtoRealizado) {
        log.info("Iniciando busca de ordens de serviço por condição de nota fiscal. Oficina ID: {}, NF Realizada: {}, Pagamento Realizado: {}", idOficina, nfRealizada, pagtoRealizado);
        ViewNotaFiscal kpiNotaFiscal = ORDEM_SERVICO_SERVICE.exibirKpiNotaFiscal(idOficina);
        List<OrdemDeServico> ordensNotaFiscalpendente = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(nfRealizada, pagtoRealizado, idOficina);

        log.info("KPI de Notas Fiscais pendentes recuperado: {} para oficina ID: {}", kpiNotaFiscal.quantidadeNfsPendentes(), idOficina);
        return AnaliseFinanceiraMapper.toResponseNotaFiscalPendente(kpiNotaFiscal.quantidadeNfsPendentes(), ordensNotaFiscalpendente);
    }
}
