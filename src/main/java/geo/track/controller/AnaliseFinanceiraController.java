package geo.track.controller;

import geo.track.dto.analise.financeira.response.ResponseNotaFiscals;
import geo.track.dto.analise.financeira.response.ResponsePagamentos;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.service.AnaliseFinanceiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/analise-financeira")
@RequiredArgsConstructor
public class AnaliseFinanceiraController {
    private final AnaliseFinanceiraService ANALISE_FINANCEIRA_SERVICE;

    @GetMapping()
    public ResponseEntity<HashMap<String, Object>> findOrdemByIdAnaliseFinanceira(@AuthenticationPrincipal UsuarioDetalhesDto usuario) {
        Integer idOficina = usuario.getIdOficina();

        ResponsePagamentos responsePagtoPendente = ANALISE_FINANCEIRA_SERVICE.findOrdensPagtoCondition(idOficina, false, false);
        ResponseNotaFiscals responseNF = ANALISE_FINANCEIRA_SERVICE.findOrdensNotaFiscalCondition(idOficina, false, true);
        ResponsePagamentos responsePagtoRealizado = ANALISE_FINANCEIRA_SERVICE.findOrdensPagtoCondition(idOficina, true, true);

        HashMap<String, Object> returnObj = new HashMap<>();
        returnObj.put("SERVICOS_PAGAMENTO_PENDENTE", responsePagtoPendente);
        returnObj.put("SERVICOS_NOTA_FISCAL_PENDENTE", responseNF);
        returnObj.put("SERVICOS_PAGAMENTO_REALIZADO", responsePagtoRealizado);

        return ResponseEntity.status(200).body(returnObj);
    }
}
