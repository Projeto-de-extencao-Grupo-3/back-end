package geo.track.dto.oficinas.request;

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
