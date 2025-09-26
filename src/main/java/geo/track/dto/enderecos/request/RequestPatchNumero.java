package geo.track.dto.enderecos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPatchNumero {
    @NotNull
    Integer id;
    @NotBlank
    Integer numero;
}
