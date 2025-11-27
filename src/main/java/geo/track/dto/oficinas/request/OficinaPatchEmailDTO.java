package geo.track.dto.oficinas.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@AllArgsConstructor
public class OficinaPatchEmailDTO {
    @NotNull
    private Integer id;
    @NotBlank
    private String email;
}
