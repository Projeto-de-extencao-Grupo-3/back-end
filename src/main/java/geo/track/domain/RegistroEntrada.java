package geo.track.domain;

import io.swagger.v3.oas.annotations.media.Schema; // Import adicionado
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa o registro de entrada de um veículo na oficina") // Adicionado
public class RegistroEntrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do registro de entrada", example = "1") // Adicionado
    private Integer idRegistroEntrada;

    @NotNull
    @Schema(description = "Data prevista para a entrada do veículo", example = "2025-10-26", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private LocalDate dataEntradaPrevista;

    @Schema(description = "Data efetiva em que o veículo deu entrada", example = "2025-10-27") // Adicionado
    private LocalDate dataEntradaEfetiva;

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
    private Boolean chave_roda;

    @Schema(description = "Quantidade de geladeiras no veículo (para caminhões/motorhomes)", example = "1") // Adicionado
    private Integer geladeira;

    @Schema(description = "Quantidade de monitores/TVs no veículo", example = "2") // Adicionado
    private Integer monitor;

    @ManyToOne
    @JoinColumn(name = "fk_veiculo")
    @Schema(description = "Veículo associado a este registro de entrada") // Adicionado
    private Veiculos fk_veiculo;

    @OneToOne(mappedBy = "fk_entrada", cascade = CascadeType.ALL)
    @Schema(description = "Ordem de serviço gerada a partir desta entrada") // Adicionado
    private OrdemDeServicos ordemDeServicos;
}