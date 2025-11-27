package geo.track.dto.clientes.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Objeto de requisição para atualizar (PUT) todos os dados de um cliente")
public class RequestPutCliente {

    @NotNull
    @Schema(description = "ID do cliente a ser atualizado", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idCliente;

    @NotBlank
    @Schema(description = "Nome completo do cliente", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @NotBlank
    @Size(min = 11, max = 14)
    @Schema(description = "CPF (11 dígitos) ou CNPJ (14 dígitos) do cliente", example = "12345678909", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cpfCnpj;

    @NotBlank
    @Size(min = 10, max = 11)
    @Schema(description = "Número de telefone do cliente (com DDD)", example = "11987654321", requiredMode = Schema.RequiredMode.REQUIRED)
    private String telefone;

    @NotBlank
    @Email
    @Schema(description = "Endereço de e-mail do cliente", example = "joao.silva@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}