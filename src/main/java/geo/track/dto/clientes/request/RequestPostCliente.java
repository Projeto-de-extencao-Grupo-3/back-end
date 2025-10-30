package geo.track.dto.clientes.request;

import geo.track.enums.cliente.TipoCliente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@AllArgsConstructor
public class RequestPostCliente {
    @NotBlank
    @Schema(description = "Nome completo do cliente", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String nome;

    @NotBlank
    @CPF
    @Schema(description = "CPF (11 dígitos) ou CNPJ (14 dígitos) do cliente", example = "12345678909", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String cpfCnpj;

    @NotBlank
    @Size(min = 10, max = 11)
    @Schema(description = "Número de telefone do cliente (com DDD)", example = "11987654321", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String telefone;

    @NotBlank
    @Schema(description = "Endereço de e-mail do cliente", example = "joao.silva@email.com", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String email;

    @NotBlank
    @Schema(description = "Tipo de cliente", example = "PESSOA_FISICA", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private TipoCliente tipoCliente;

    @NotBlank
    @Schema(description = "Endereço associado a este cliente", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private Integer fkEndereco;

    @NotBlank
    @Schema(description = "Oficina associada a este cliente", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private Integer fkOficina;
}
