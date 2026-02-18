package geo.track.controller;

import geo.track.dto.analise.financeira.response.ResponseNotaFiscals;
import geo.track.dto.analise.financeira.response.ResponsePagamentos;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.dto.os.response.*;
import geo.track.mapper.AnaliseFinanceiraMapper;
import geo.track.service.AnaliseFinanceiraService;
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
    private final AnaliseFinanceiraService analiseService;

    @GetMapping()
    public ResponseEntity<HashMap<String, Object>> findOrdemByIdAnaliseFinanceira(@AuthenticationPrincipal UsuarioDetalhesDto usuario) {
        Integer idOficina = usuario.getIdOficina();

        ResponsePagamentos responsePagtoPendente = analiseService.findOrdensPagtoCondition(idOficina, false, false);
        ResponseNotaFiscals responseNF = analiseService.findOrdensNotaFiscalCondition(idOficina, false, true);
        ResponsePagamentos responsePagtoRealizado = analiseService.findOrdensPagtoCondition(idOficina, true, true);

        HashMap<String, Object> returnObj = new HashMap<>();
        returnObj.put("servicos_pagamento_pendente", responsePagtoPendente);
        returnObj.put("servicos_nota_fiscal_pendente", responseNF);
        returnObj.put("servicos_pagamento_realizado", responsePagtoRealizado);

        return ResponseEntity.status(200).body(returnObj);
    }
}
