package geo.track.dto.clientes.request;

import jakarta.validation.constraints.NotBlank;

public record RequestPostContato(
        @NotBlank
        String telefone,
        @NotBlank
        String email,
        @NotBlank
        String nomeContato,
        @NotBlank
        String departamentoContato
) {
}
