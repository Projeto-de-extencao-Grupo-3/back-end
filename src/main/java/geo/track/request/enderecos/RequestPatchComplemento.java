package geo.track.request.enderecos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPatchComplemento {
    @NotNull
    Integer id;
    @NotBlank
    String complemento;
}
