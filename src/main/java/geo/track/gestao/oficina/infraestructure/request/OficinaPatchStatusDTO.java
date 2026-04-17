package geo.track.gestao.oficina.infraestructure.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OficinaPatchStatusDTO {

    @NotNull
    private Integer id;
    @NotNull
    private Boolean status;
}
