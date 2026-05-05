package geo.track.jornada.domain.listagem;

import geo.track.jornada.application.listagem.ListagemAnaliseFinanceiraUseCase;
import geo.track.jornada.domain.OrdemDeServicoService;
import geo.track.jornada.infraestructure.mapper.AnaliseFinanceiraMapper;
import geo.track.jornada.infraestructure.request.ListagemJornadaParams;
import geo.track.jornada.infraestructure.response.listagem.ListagemJornadaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ListagemAnaliseFinanceiraSemAnoMes implements ListagemAnaliseFinanceiraUseCase {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @Override
    public ListagemJornadaResponse execute(ListagemJornadaParams request) {
        var listPagtoNaoRealizado = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(false, false);
        var kpiPagamentoPendente = ORDEM_SERVICO_SERVICE.exibirKpiPagtoPendente();

        var listSemNotaFiscal = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(false, true);
        var kpiNotaFiscalPendente = ORDEM_SERVICO_SERVICE.exibirKpiNotaFiscal();

        var listSucesso = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(true, true);
        var kpiPagamentoRealizado = ORDEM_SERVICO_SERVICE.exibirKpiPagtoRealizado();

         Long totalPagamentosRealizados = kpiPagamentoRealizado.getQuantidadePagamentosRealizadosAsLong();
         Long quantidadeNfsPendentes = kpiNotaFiscalPendente.getQuantidadeNfsPendentesAsLong();
         Long quantidadeServicosNaoPagos = kpiPagamentoPendente.getQuantidadeServicosNaoPagosAsLong();
         Double totalValorNaoPago = kpiPagamentoPendente.getTotalValorNaoPagoAsDouble();

        var responsePagamentoRealizado = AnaliseFinanceiraMapper.toResponsePagamentoRealizado(totalPagamentosRealizados, listSucesso);
        var responseNotaFiscalPendente = AnaliseFinanceiraMapper.toResponseNotaFiscalPendente(quantidadeNfsPendentes, listSemNotaFiscal);
        var responsePagamentoPendente = AnaliseFinanceiraMapper.toResponsePagamentoPendente(totalValorNaoPago, quantidadeServicosNaoPagos, listPagtoNaoRealizado);

        var response = new HashMap<String, Object>();
        response.put("SERVICOS_PAGAMENTO_PENDENTE", responsePagamentoPendente);
        response.put("SERVICOS_NOTA_FISCAL_PENDENTE", responseNotaFiscalPendente);
        response.put("SERVICOS_PAGAMENTO_REALIZADO", responsePagamentoRealizado);

        return ListagemJornadaResponse.builder().listagemAnaliseFinanceira(response).build();
    }
}
