package geo.track.jornada.service.listagem;

import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.request.ListagemJornadaParams;
import geo.track.jornada.response.listagem.ListagemJornadaResponse;
import geo.track.jornada.response.listagem.ResponseNotaFiscals;
import geo.track.jornada.response.listagem.ResponsePagamentos;
import geo.track.mapper.AnaliseFinanceiraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ListagemAnaliseFinanceiraStrategy implements ListagemJornadaStrategy<ListagemJornadaParams> {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Override
    public ListagemJornadaResponse execute(ListagemJornadaParams request) {
        Integer ano = Integer.valueOf(request.anoMes().split("-")[0]);
        Integer mes = Integer.valueOf(request.anoMes().split("-")[1]);

        var listPagtoNaoRealizado = ORDEM_SERVICO_REPOSITORY.findByListagemAnaliseFinanceiraStrategy(false, false, ano, mes);
        var kpiPagtoNaoRealizado = ORDEM_SERVICO_REPOSITORY.findViewPagamentoPendente(ano, mes);

        var listSemNotaFiscal = ORDEM_SERVICO_REPOSITORY.findByListagemAnaliseFinanceiraStrategy(false, true, ano, mes);
        var kpiSemNotaFiscal = ORDEM_SERVICO_REPOSITORY.findViewNotasFicaisPendentes(ano, mes);

        var listSucesso = ORDEM_SERVICO_REPOSITORY.findByListagemAnaliseFinanceiraStrategy(false, false, ano, mes);
        var kpiSucesso = ORDEM_SERVICO_REPOSITORY.findViewPagamentoRealizados(ano, mes);

        Long totalPagamentosRealizados = kpiSucesso == null ? 0L : kpiSucesso.totalPagamentosRealizados();
        Long quantidadeNfsPendentes = kpiSemNotaFiscal == null ? 0L : kpiSemNotaFiscal.quantidadeNfsPendentes();
        Long quantidadeServicosNaoPagos = kpiPagtoNaoRealizado == null ? 0L : kpiPagtoNaoRealizado.quantidadeServicosNaoPagos();
        Double totalValorNaoPago = kpiPagtoNaoRealizado == null ? 0.0 : kpiPagtoNaoRealizado.totalValorNaoPago();

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
