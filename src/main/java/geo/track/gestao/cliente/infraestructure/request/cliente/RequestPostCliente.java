package geo.track.gestao.cliente.infraestructure.request.cliente;

import geo.track.gestao.cliente.infraestructure.request.contato.RequestPostContato;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPostEndereco;
import geo.track.gestao.cliente.infraestructure.persistence.entity.TipoCliente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RequestPostCliente {
    @NotBlank
    @Schema(description = "Nome completo do cliente", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String nome;

    @NotBlank
    @Size(min = 11, max = 14)
    @Schema(description = "CPF (11 dígitos) ou CNPJ (14 dígitos) do cliente", example = "12345678909", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String cpfCnpj;

    @Schema(description = "Inscrição estadual do cliente (opcional, apenas para pessoas jurídicas)", example = "123456789", requiredMode = Schema.RequiredMode.NOT_REQUIRED) // Adicionado
    private String inscricaoEstadual;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo de cliente", example = "PESSOA_FISICA", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private TipoCliente tipoCliente;

    @NotNull
    @Schema(description = "Oficina associada a este cliente", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private Integer fkOficina;

    @NotNull
    @Schema(description = "Lista de contatos do cliente", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<RequestPostContato> contatos;

    @NotNull
    @Schema(description = "Endereço do cliente", requiredMode = Schema.RequiredMode.REQUIRED)
    private RequestPostEndereco endereco;
}
