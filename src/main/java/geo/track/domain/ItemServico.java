package geo.track.domain;

import geo.track.enums.Servico;
import geo.track.enums.servico.LadoVeiculo;
import geo.track.enums.servico.ParteVeiculo;
import geo.track.enums.servico.TipoPintura;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto utilizado para armazenar informações dos serviços") // 1. Descrição do objeto
@Table(name = "itens_servicos")
public class ItemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID da lista de serviços", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idRegistroServico;

    @NotNull
    @Schema(description = "Preço cobrado", example = "99.90", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double precoCobrado;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(description = "Parte que será reparada no veiculo", example = "Parachoque/Parabrisa/Janelas", requiredMode = Schema.RequiredMode.REQUIRED)
    private ParteVeiculo parteVeiculo;

    @NotNull
    @Schema(description = "Lado no qual será executado o reparo", example = "Latera Esquerda/Transeira", requiredMode = Schema.RequiredMode.REQUIRED)
    @Enumerated(EnumType.STRING)
    private LadoVeiculo ladoVeiculo;

    @Schema(description = "Cor do Veiculo", example = "Ciano/Vermelho", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cor;

    @NotBlank
    @Schema(description = "Descrição detalahada do serviço", example = "Troca do parabrisa trincado por um novo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String especificacaoServico;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Campo para colocar alguma observação se necessário", example = "Desconto de 10%, pois a peça nova X foi danificada", requiredMode = Schema.RequiredMode.REQUIRED)
    private TipoPintura tipoPintura;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo serviço ")
    private Servico tipoServico;

    @ManyToOne
    @JoinColumn(name = "fk_ordem_servico")
    @Schema(description = "FK da ordem de serviço ")
    private OrdemDeServico fkOrdemServico;
}
