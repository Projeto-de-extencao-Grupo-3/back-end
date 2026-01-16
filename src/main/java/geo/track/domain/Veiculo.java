package geo.track.domain;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Representa a entidade Veículo no sistema")
@Table(name = "veiculos")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do Veículo", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idVeiculo;

    @NotBlank
    @Size(min = 7, max = 7)
    @Schema(description = "Placa do veículo (formato Mercosul ou antigo)", example = "BRA2E19", requiredMode = Schema.RequiredMode.REQUIRED)
    private String placa;

    @NotBlank
    @Schema(description = "Marca do veículo", example = "Volkswagen", requiredMode = Schema.RequiredMode.REQUIRED)
    private String marca;

    @NotBlank
    @Schema(description = "Modelo do veículo", example = "Nivus", requiredMode = Schema.RequiredMode.REQUIRED)
    private String modelo;

    @NotBlank
    @Schema(description = "Prefixo do veículo", example = "1200", requiredMode = Schema.RequiredMode.REQUIRED)
    private String prefixo;

    @NotNull
    @Positive
    @Digits(integer = 4, fraction = 0)
    @Schema(description = "Ano do modelo do veículo", example = "2023", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer anoModelo;

    @ManyToOne
    @JoinColumn(name = "fk_proprietario")
    @Schema(description = "Cliente proprietário deste veículo")
    private Cliente fkCliente;

    @OneToMany(mappedBy = "fkVeiculo")
    @Schema(description = "Histórico de registros de entrada deste veículo na oficina")
    private List<RegistroEntrada> registroEntradas;
}