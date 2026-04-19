package geo.track.jornada.domain.listagem;

import geo.track.jornada.application.listagem.ListagemAnaliseFinanceiraUseCase;
import geo.track.jornada.infraestructure.request.ListagemJornadaParams;
import geo.track.jornada.infraestructure.response.listagem.ListagemJornadaResponse;
import geo.track.jornada.domain.OrdemDeServicoService;
import geo.track.jornada.infraestructure.mapper.AnaliseFinanceiraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ListagemAnaliseFinanceira implements ListagemAnaliseFinanceiraUseCase {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @Override
    public ListagemJornadaResponse execute(ListagemJornadaParams request) {
        Integer ano = Integer.valueOf(request.anoMes().split("-")[0]);
        Integer mes = Integer.valueOf(request.anoMes().split("-")[1]);

        var listPagtoNaoRealizado = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(false, false, ano, mes);
        var kpiPagamentoPendente = ORDEM_SERVICO_SERVICE.exibirKpiPagtoPendente(ano, mes);

        var listSemNotaFiscal = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(false, true, ano, mes);
        var kpiNotaFiscalPendente = ORDEM_SERVICO_SERVICE.exibirKpiNotaFiscal(ano, mes);

        var listSucesso = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(true, true, ano, mes);
        var kpiPagamentoRealizado = ORDEM_SERVICO_SERVICE.exibirKpiPagtoRealizado(ano, mes);

        Long totalPagamentosRealizados = kpiPagamentoRealizado.totalPagamentosRealizados();
        Long quantidadeNfsPendentes = kpiNotaFiscalPendente.quantidadeNfsPendentes();
        Long quantidadeServicosNaoPagos = kpiPagamentoPendente.quantidadeServicosNaoPagos();
        Double totalValorNaoPago = kpiPagamentoPendente.totalValorNaoPago();

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
