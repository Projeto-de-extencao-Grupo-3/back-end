package geo.track.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto utilizado para armazenar informações dos serviços") // 1. Descrição do objeto
public class ItensServicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID da lista de serviços", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idRegistroServico;

    @NotNull
    @Schema(description = "Preço cobrado", example = "99.90", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double precoCobrado;

    @NotBlank
    @Schema(description = "Parte que será reparada no veiculo", example = "Parachoque/Parabrisa/Janelas", requiredMode = Schema.RequiredMode.REQUIRED)
    private String parteVeiculo;

    @NotBlank
    @Schema(description = "Lado no qual será executado o reparo", example = "Latera Esquerda/Transeira", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ladoVeiculo;

    @NotBlank
    @Schema(description = "Cor do Veiculo", example = "Ciano/Vermelho", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cor;

    @NotBlank
    @Schema(description = "Descrição detalahada do serviço", example = "Troca do parabrisa trincado por um novo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String especificacaoServico;

    @Schema(description = "Campo para colocar alguma observação se necessário", example = "Desconto de 10%, pois a peça nova X foi danificada", requiredMode = Schema.RequiredMode.REQUIRED)
    private String observacoesItem;

    @ManyToOne
    @JoinColumn(name = "fk_servico")
    @Schema(description = "FK do serviço ")
    private Servicos fkServico;

    @ManyToOne
    @JoinColumn(name = "fk_ordem_servico")
    @Schema(description = "FK da ordem de serviço ")
    private OrdemDeServicos fkOrdemServico;

    @OneToMany(mappedBy = "fkServicoFunc", cascade = CascadeType.ALL)
    @Schema(description = "Lista de funcionarios associados à uma lista de serviços")
    private List<FuncionariosServico> listaFuncionario;

}
