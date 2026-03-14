package geo.track.dto.os.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPatchSeguradora {
    @NotNull
    private Integer idOrdem;
    @NotNull
    private Boolean seguradora;
}
