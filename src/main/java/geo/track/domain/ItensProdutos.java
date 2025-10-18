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
@Schema(description = "Objeto utilizado para armazenar informações dos produtos") // 1. Descrição do objeto
public class ItensProdutos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id da lista de produtos", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idItensProdutos;

    @NotBlank
    @Schema(description = "Quatidade do produto selecionado", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantidade;

    @NotBlank
    @Schema(description = "Preço da peça selecionada", example = "29.90", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double precoPeca;

    private Integer fkOrdemServico;
    private Integer fkPeca;
}
