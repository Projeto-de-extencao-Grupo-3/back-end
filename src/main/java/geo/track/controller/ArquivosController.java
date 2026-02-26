package geo.track.controller;

import geo.track.domain.OrdemDeServico;
import geo.track.dto.arquivos.RequestGetArquivoOrcamento;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.exception.BadRequestException;
import geo.track.exception.constraint.message.EnumDomains;
import geo.track.mapper.OrdemDeServicoMapper;
import geo.track.gateway.GatewayExportData;
import geo.track.service.OrdemDeServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/arquivos")
public class ArquivosController{
    private final GatewayExportData GATEWAY_EXPORT_DATA;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @PostMapping("/orcamento")
    public ResponseEntity<byte[]> post(@AuthenticationPrincipal UsuarioDetalhesDto usuario, @RequestHeader("authorization") String token, @RequestBody @Valid RequestGetArquivoOrcamento body) {
        Integer idUsuario = usuario.getIdOficina();
        OrdemDeServico orcamento = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(body.idOrcamento(), idUsuario);

        byte[] pdfContent = GATEWAY_EXPORT_DATA.getArquivoOrcamento(token, OrdemDeServicoMapper.toResponse(orcamento));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        StringBuilder nome = new StringBuilder();
        String[] nomeCompleto = orcamento.getFkEntrada().getFkVeiculo().getFkCliente().getNome().toUpperCase().split(" ");
        for (String palavra : nomeCompleto) {
            nome.append(String.format("%s_", palavra));
        }
        DecimalFormat df = new DecimalFormat("0000");
        headers.setContentDispositionFormData("attachment", String.format("ORC%s_%s%s.pdf", df.format(orcamento.getIdOrdemServico()), nome, orcamento.getFkEntrada().getFkVeiculo().getPlaca()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }

    @PostMapping("/ordem_servico")
    public ResponseEntity<byte[]> posta(@AuthenticationPrincipal UsuarioDetalhesDto usuario, @RequestHeader("authorization") String token,@RequestBody @Valid RequestGetArquivoOrcamento body) {
        Integer idOficina = usuario.getIdOficina();
        OrdemDeServico orcamento = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(body.idOrcamento(), idOficina);

        if (orcamento.getServicos().isEmpty()) throw new BadRequestException("Este orçamento não possui serviços", EnumDomains.ORDEM_DE_SERVICO);
        if (orcamento.getDataSaidaEfetiva() == null) orcamento.setDataSaidaEfetiva(LocalDate.now());

        byte[] pdfContent = GATEWAY_EXPORT_DATA.getArquivoOrdemServico(token, OrdemDeServicoMapper.toResponse(orcamento));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        StringBuilder nome = new StringBuilder();
        String[] nomeCompleto = orcamento.getFkEntrada().getFkVeiculo().getFkCliente().getNome().toUpperCase().split(" ");
        for (String palavra : nomeCompleto) {
            nome.append(String.format("%s_", palavra));
        }
        DecimalFormat df = new DecimalFormat("0000");
        headers.setContentDispositionFormData("attachment", String.format("OS%s_%s%s.pdf", df.format(orcamento.getIdOrdemServico()), nome, orcamento.getFkEntrada().getFkVeiculo().getPlaca()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }
}
