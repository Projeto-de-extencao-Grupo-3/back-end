package geo.track.controller;

import geo.track.domain.OrdemDeServico;
import geo.track.dto.arquivos.RequestGetArquivoOrcamento;
import geo.track.exception.BadRequestException;
import geo.track.mapper.OrdemDeServicoMapper;
import geo.track.port.GatewayExportData;
import geo.track.service.OrdemDeServicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/arquivos")
@SecurityRequirement(name = "Bearer")
public class ArquivosController {
    private final GatewayExportData gatewayExportData;
    private final OrdemDeServicoService ordemServicoService;

    @PostMapping("/orcamento")
    public ResponseEntity<byte[]> post(@RequestHeader("authorization") String token, @RequestBody @Valid RequestGetArquivoOrcamento body) {
        OrdemDeServico orcamento = ordemServicoService.findOrdemById(body.idOrcamento());

        System.out.println(token);

        byte[] pdfContent = gatewayExportData.getArquivoOrcamento(token, OrdemDeServicoMapper.toResponse(orcamento));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        StringBuilder nome = new StringBuilder();
        String[] nomeCompleto = orcamento.getFk_entrada().getFkVeiculo().getFkCliente().getNome().toUpperCase().split(" ");
        for (String palavra : nomeCompleto) {
            nome.append(String.format("%s_", palavra));
        }
        DecimalFormat df = new DecimalFormat("0000");
        headers.setContentDispositionFormData("attachment", String.format("ORC%s_%s%s.pdf", df.format(orcamento.getIdOrdemServico()), nome, orcamento.getFk_entrada().getFkVeiculo().getPlaca()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }

    @PostMapping("/ordem_servico")
    public ResponseEntity<byte[]> posta(@RequestHeader("authorization") String token,@RequestBody @Valid RequestGetArquivoOrcamento body) {
        OrdemDeServico orcamento = ordemServicoService.findOrdemById(body.idOrcamento());

        if (orcamento.getServicos().isEmpty()) throw new BadRequestException("Este orçamento não possui serviços", "Ordem de Serviço");
        if (orcamento.getDtSaidaEfetiva() == null) orcamento.setDtSaidaEfetiva(LocalDate.now());

        byte[] pdfContent = gatewayExportData.getArquivoOrdemServico(token, OrdemDeServicoMapper.toResponse(orcamento));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        StringBuilder nome = new StringBuilder();
        String[] nomeCompleto = orcamento.getFk_entrada().getFkVeiculo().getFkCliente().getNome().toUpperCase().split(" ");
        for (String palavra : nomeCompleto) {
            nome.append(String.format("%s_", palavra));
        }
        DecimalFormat df = new DecimalFormat("0000");
        headers.setContentDispositionFormData("attachment", String.format("OS%s_%s%s.pdf", df.format(orcamento.getIdOrdemServico()), nome, orcamento.getFk_entrada().getFkVeiculo().getPlaca()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }
}

