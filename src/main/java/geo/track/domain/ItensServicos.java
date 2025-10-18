package geo.track.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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
public class ItensServicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID da lista de serviços", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idItensServicos;

    @NotBlank
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

    @Schema(description = "Campo para colocar alguma observação se necessário", example = "Não sei", requiredMode = Schema.RequiredMode.REQUIRED)
    private String observacoesItem;

    private Integer fkOrderServico;
    private Integer fkServico;

}
