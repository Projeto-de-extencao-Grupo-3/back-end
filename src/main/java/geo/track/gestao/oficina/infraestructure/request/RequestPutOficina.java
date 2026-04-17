package geo.track.gestao.oficina.infraestructure.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestPutOficina {
    @NotNull
    private Integer idOficina;

    @Email
    private String email;

    private Boolean status;
}
