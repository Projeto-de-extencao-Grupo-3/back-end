package geo.track.controller;

import geo.track.domain.OrdemDeServico;
import geo.track.dto.arquivos.RequestGetArquivoOrcamento;
import geo.track.mapper.OrdemDeServicoMapper;
import geo.track.port.GatewayExportData;
import geo.track.service.OrdemDeServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/arquivos")
public class ArquivosController {
    private final GatewayExportData gatewayExportData;
    private final OrdemDeServicoService ordemServicoService;

    @PostMapping("/ordem-servico")
    public ResponseEntity<byte[]> post(@RequestBody @Valid RequestGetArquivoOrcamento body) {
        OrdemDeServico orcamento = ordemServicoService.findOrdemById(body.idOrcamento());

        byte[] pdfContent = gatewayExportData.getData(OrdemDeServicoMapper.toResponse(orcamento));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "orcamento.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }
}

