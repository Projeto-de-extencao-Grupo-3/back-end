package geo.track.dto.oficinas.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class OficinaPatchStatusDTO {

    @NotNull
    private Integer id;
    @NotNull
    private Boolean status;
}
