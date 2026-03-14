package geo.track.dto.os.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPatchNfRealizada {
    @NotNull
    private Integer idOrdem;
    @NotNull
    private Boolean nfRealizada;
}
