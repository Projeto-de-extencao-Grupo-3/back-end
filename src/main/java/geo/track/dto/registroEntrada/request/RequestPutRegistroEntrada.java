package geo.track.dto.registroEntrada.request;

import io.swagger.v3.oas.annotations.media.Schema; // Import adicionado
import jakarta.validation.constraints.NotNull; // Import adicionado
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF; // Import adicionado

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Schema(description = "Objeto de requisição para atualizar (PUT) um registro de entrada existente") // Adicionado
public class RequestPutRegistroEntrada {

    @NotNull
    @Schema(description = "ID do registro de entrada a ser atualizado", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private Integer idRegistro;

    @Schema(description = "Data efetiva em que o veículo deu entrada", example = "2025-10-27") // Adicionado
    private LocalDate dtEntradaEfetiva;

    @Schema(description = "Nome do responsável que trouxe o veículo", example = "Carlos Souza") // Adicionado
    private String responsavel;

    @CPF
    @Schema(description = "CPF do responsável", example = "98765432109") // Adicionado
    private String cpf;

    @Schema(description = "Indica se o veículo possui extintor", example = "true") // Adicionado
    private Boolean extintor;

    @Schema(description = "Indica se o veículo possui macaco", example = "true") // Adicionado
    private Boolean macaco;

    @Schema(description = "Indica se o veículo possui chave de roda", example = "true") // Adicionado
    private Boolean chaveRoda;

    @Schema(description = "Quantidade de geladeiras no veículo", example = "1") // Adicionado
    private Integer geladeira;

    @Schema(description = "Quantidade de monitores/TVs no veículo", example = "2") // Adicionado
    private Integer monitor;

    @Schema(description = "ID do veículo associado ao registro (caso precise ser corrigido)", example = "1") // Adicionado
    private Integer fkVeiculo;
}