package geo.track.externo.arquivo.infraestructure.web;

import geo.track.externo.arquivo.infraestructure.request.RequestGetArquivoOrcamento;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Bearer")
public interface ArquivosSwagger {

    @PostMapping("/orcamento")
    ResponseEntity<byte[]> post(@RequestHeader("authorization") String token, @RequestBody @Valid RequestGetArquivoOrcamento body);

    @PostMapping("/ordem_servico")
    ResponseEntity<byte[]> posta(@RequestHeader("authorization") String token, @RequestBody @Valid RequestGetArquivoOrcamento body);
}
