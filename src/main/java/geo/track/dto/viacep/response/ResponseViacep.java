package geo.track.dto.viacep.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Objeto utilizado para receber o resultado da busca por CEP") // 1. Descrição do objeto
public class ResponseViacep {
    @Schema(description = "CEP do Endereço", example = "01414001", requiredMode = Schema.RequiredMode.REQUIRED) // 2. Detalhes do campo
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
}
