package geo.track.controller;

import geo.track.dto.analise.financeira.response.ResponseNotaFiscalPendente;
import geo.track.dto.analise.financeira.response.ResponsePagamentos;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.dto.os.response.*;
import geo.track.mapper.AnaliseFinanceiraMapper;
import geo.track.service.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/analise-financeira")
@RequiredArgsConstructor
public class AnaliseFinanceiraController {
    private final OrdemDeServicoService ordemService;

    @GetMapping()
    public ResponseEntity<HashMap<String, Object>> findOrdemByIdAnaliseFinanceira(@AuthenticationPrincipal UsuarioDetalhesDto usuario) {
        Integer idOficina = usuario.getIdOficina();

        ViewPagtoPendente kpiPagamentoPendente = ordemService.findKpiPagamentoPendente(idOficina);
        List<OrdemDeServicoResponse> ordensPagtoPendente = ordemService.findOrdemByNfRealizadaAndPagtRealizado(false, false, idOficina);
        ResponsePagamentos responsePagtoPendente = AnaliseFinanceiraMapper.toResponsePagamentoPendente(kpiPagamentoPendente.totalValorNaoPago(), kpiPagamentoPendente.quantidadeServicosNaoPagos(), ordensPagtoPendente);

        ViewNotaFiscal kpiNotaFiscal = ordemService.findKpiNotaFiscal(idOficina);
        List<OrdemDeServicoResponse> ordensNotaFiscalpendente = ordemService.findOrdemByNfRealizadaAndPagtRealizado(false, true, idOficina);
        ResponseNotaFiscalPendente responseNF = AnaliseFinanceiraMapper.toResponseNotaFiscalPendente(kpiNotaFiscal.quantidadeNfsPendentes(), ordensNotaFiscalpendente);

        ViewPagtoRealizado kpiPagamentoRealizado = ordemService.findKpiPagamentoRealizado(idOficina);
        List<OrdemDeServicoResponse> ordensPagtoRealizado = ordemService.findOrdemByNfRealizadaAndPagtRealizado(true, true, idOficina);
        ResponsePagamentos responsePagtoRealizado = AnaliseFinanceiraMapper.toResponsePagamentoRealizado(kpiPagamentoRealizado.totalPagamentosRealizados(), ordensPagtoRealizado);

        HashMap<String, Object> returnObj = new HashMap<>();
        returnObj.put("servicos_pagamento_pendente", responsePagtoPendente);
        returnObj.put("servicos_nota_fiscal_pendente", responseNF);
        returnObj.put("servicos_pagamento_realizado", responsePagtoRealizado);

        return ResponseEntity.status(200).body(returnObj);
    }
}
