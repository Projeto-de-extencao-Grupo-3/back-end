package geo.track.dto.oficinas.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestPostOficina {

    @NotBlank
    private String razaoSocial;

    @NotBlank
    private String cnpj;

    @Email
    @NotBlank
    private String email;

    private Boolean status = true;
}
