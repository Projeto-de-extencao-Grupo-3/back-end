package geo.track.service;

import geo.track.dto.analise.financeira.response.ResponseNotaFiscals;
import geo.track.dto.analise.financeira.response.ResponsePagamentos;
import geo.track.dto.os.response.OrdemDeServicoResponse;
import geo.track.dto.os.response.ViewNotaFiscal;
import geo.track.dto.os.response.ViewPagtoPendente;
import geo.track.mapper.AnaliseFinanceiraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnaliseFinanceiraService {
    private final OrdemDeServicoService ordemService;

    public ResponsePagamentos findOrdensPagtoCondition(Integer idOficina, Boolean nfRealizada, Boolean pagtoRealizado) {
        ViewPagtoPendente kpiPagamentoPendente = ordemService.findKpiPagamentoPendente(idOficina);
        List<OrdemDeServicoResponse> ordensPagtoPendente = ordemService.findOrdemByNfRealizadaAndPagtRealizado(nfRealizada, pagtoRealizado, idOficina);

        return AnaliseFinanceiraMapper.toResponsePagamentoPendente(kpiPagamentoPendente.totalValorNaoPago(), kpiPagamentoPendente.quantidadeServicosNaoPagos(), ordensPagtoPendente);
    }

    public ResponseNotaFiscals findOrdensNotaFiscalCondition(Integer idOficina, Boolean nfRealizada, Boolean pagtoRealizado) {
        ViewNotaFiscal kpiNotaFiscal = ordemService.findKpiNotaFiscal(idOficina);
        List<OrdemDeServicoResponse> ordensNotaFiscalpendente = ordemService.findOrdemByNfRealizadaAndPagtRealizado(nfRealizada, pagtoRealizado, idOficina);
        return AnaliseFinanceiraMapper.toResponseNotaFiscalPendente(kpiNotaFiscal.quantidadeNfsPendentes(), ordensNotaFiscalpendente);

    }
}
