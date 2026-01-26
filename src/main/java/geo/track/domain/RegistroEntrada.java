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
@Table(name = "registro_entrada")
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

    @Schema(description = "Quantidade de extintor no veículo", example = "1") // Adicionado
    private Integer extintor;

    @Schema(description = "Quantidade de macaco no veículo", example = "1") // Adicionado
    private Integer macaco;

    @Schema(description = "Quantidade de chaves de roda no veículo", example = "1") // Adicionado
    private Integer chaveRoda;

    @Schema(description = "Quantidade de geladeiras no veículo", example = "1") // Adicionado
    private Integer geladeira;

    @Schema(description = "Quantidade de monitores/TVs no veículo", example = "2") // Adicionado
    private Integer monitor;

    @Schema(description = "Quantidade de estepes no veículo", example = "2") // Adicionado
    private Integer estepe;

    @Schema(description = "Quantidade de Som/DVD no veículo", example = "2") // Adicionado
    private Integer somDvd;

    @Schema(description = "Quantidade de caixa de ferramentas no veículo", example = "2") // Adicionado
    private Integer caixaFerramentas;

//    @Schema(description = "Observações sobre a entrada do Veículo", example = "Entrou com um suporte para TV no Bagageiro") // Adicionado
//    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "fk_veiculo")
    @Schema(description = "Veículo associado a este registro de entrada") // Adicionado
    private Veiculo fkVeiculo;

    @OneToOne(mappedBy = "fk_entrada")
    @Schema(description = "Ordem de serviço gerada a partir desta entrada") // Adicionado
    private OrdemDeServico ordemDeServicos;
}