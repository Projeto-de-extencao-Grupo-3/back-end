package geo.track.gestao.cliente.infraestructure.request.contato;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPutContato {
    @NotBlank
    private String telefone;

    @NotBlank
    private String email;

    @NotBlank
    private String nomeContato;

    @NotBlank
    private String departamentoContato;
}
