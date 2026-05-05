package geo.track.gestao.cliente.infraestructure.request.contato;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPostContato {
    @NotBlank
    private String telefone;

    @NotBlank
    private String email;

    private String nomeContato;

    private String departamentoContato;
}

