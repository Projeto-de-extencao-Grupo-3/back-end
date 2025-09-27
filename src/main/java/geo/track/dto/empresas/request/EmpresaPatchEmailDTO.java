package geo.track.dto.empresas.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaPatchEmailDTO {
    @NotNull
    private Integer id;
    @NotBlank
    private String email;
}
