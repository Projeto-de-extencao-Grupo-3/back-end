package geo.track.domain;

import io.swagger.v3.oas.annotations.media.Schema; // Import adicionado
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa a entidade Veículo no sistema") // Adicionado
public class Veiculos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do Veículo", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private Integer idVeiculo;

    @NotBlank
    @Size(min = 7, max = 7)
    @Schema(description = "Placa do veículo (formato Mercosul ou antigo)", example = "BRA2E19", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String placa;

    @NotBlank
    @Schema(description = "Marca do veículo", example = "Volkswagen", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String marca;

    @NotBlank
    @Schema(description = "Modelo do veículo", example = "Nivus", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String modelo;

    @NotNull
    @Positive
    @Digits(integer = 4, fraction = 0)
    @Schema(description = "Ano do modelo do veículo", example = "2023", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private Integer anoModelo;

    @NotBlank
    @Schema(description = "Cor predominante do veículo", example = "Cinza", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String cor;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    @Schema(description = "Cliente proprietário deste veículo") // Adicionado
    private Clientes fkCliente;

    @OneToMany(mappedBy = "fk_veiculo", cascade = CascadeType.ALL)
    @Schema(description = "Histórico de registros de entrada deste veículo na oficina") // Adicionado
    private List<RegistroEntrada> registroEntradas;
}