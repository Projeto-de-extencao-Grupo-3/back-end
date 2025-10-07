package geo.track.dto.oficinas.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OficinaPatchStatusDTO {

    @NotNull
    private Integer id;
    @NotBlank
    private String status;
}
