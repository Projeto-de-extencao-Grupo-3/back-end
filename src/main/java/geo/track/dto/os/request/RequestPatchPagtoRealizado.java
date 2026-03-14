package geo.track.dto.os.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPatchPagtoRealizado {
    @NotNull
    private Integer idOrdem;
    @NotNull
    private Boolean pagtoRealizado;
}
