package geo.track.port;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "export-data", url = "${export.data.url}")
public interface GatewayExportData {

    @PostMapping("/api/pdf/generate")
    byte[] getData(@Valid @RequestBody Object body);
}
