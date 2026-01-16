package geo.track.controller;

import geo.track.port.GatewayExportData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arquivos")
public class ArquivosController {
    private GatewayExportData gatewayExportData;

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> post(@RequestBody Object value) {
        byte[] pdfContent = gatewayExportData.getData(value);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "orcamento.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }

    public ArquivosController(GatewayExportData gatewayExportData) {
        this.gatewayExportData = gatewayExportData;
    }
}
