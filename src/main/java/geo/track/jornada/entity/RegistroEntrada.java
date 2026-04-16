package geo.track.jornada.entity;

import geo.track.gestao.entity.Veiculo;
import io.swagger.v3.oas.annotations.media.Schema; // Import adicionado
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa o registro de entrada de um veículo na oficina")
@Table(name = "registro_entrada")
public class RegistroEntrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do registro de entrada", example = "1")
    private Integer idRegistroEntrada;

    @Schema(description = "Data prevista para a entrada do veículo", example = "2025-10-26", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dataEntradaPrevista;

    @Schema(description = "Data efetiva em que o veículo deu entrada", example = "2025-10-27")
    private LocalDate dataEntradaEfetiva;

    @Schema(description = "Nome do responsável que trouxe o veículo", example = "Carlos Souza")
    private String responsavel;

    @Schema(description = "CPF do responsável", example = "98765432109")
    private String cpf;

//    @Schema(description = "Quantidade de extintor no veículo", example = "1")
//    private Integer extintor;
//
//    @Schema(description = "Quantidade de macaco no veículo", example = "1")
//    private Integer macaco;
//
//    @Schema(description = "Quantidade de chaves de roda no veículo", example = "1")
//    private Integer chaveRoda;
//
//    @Schema(description = "Quantidade de geladeiras no veículo", example = "1")
//    private Integer geladeira;
//
//    @Schema(description = "Quantidade de monitores/TVs no veículo", example = "2")
//    private Integer monitor;
//
//    @Schema(description = "Quantidade de estepes no veículo", example = "2")
//    private Integer estepe;
//
//    @Schema(description = "Quantidade de Som/DVD no veículo", example = "2")
//    private Integer somDvd;
//
//    @Schema(description = "Quantidade de caixa de ferramentas no veículo", example = "2")
//    private Integer caixaFerramentas;
//
//    @Schema(description = "Observações sobre a entrada do Veículo", example = "Entrou com um suporte para TV no Bagageiro")
//    private String observacoes;

    @OneToMany(mappedBy = "fkRegistroEntrada", cascade = CascadeType.ALL)
    @Schema(description = "Lista de itens associados a este registro de entrada")
    private List<ItemEntrada> itensEntrada;

    @ManyToOne
    @JoinColumn(name = "fk_veiculo")
    @Schema(description = "Veículo associado a este registro de entrada")
    private Veiculo fkVeiculo;

    @OneToOne()
    @JoinColumn(name = "fk_ordem_servico")
    @Schema(description = "Ordem de serviço gerada a partir desta entrada")
    private OrdemDeServico fkOrdemServico;
}