package geo.track.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @ManyToOne
    @JoinColumn(name = "fk_produto")
    @Schema(description = " ")
    private Produtos fkPeca;

    @ManyToOne
    @JoinColumn(name = "fk_order_de_servico")
    @Schema(description = " ")
    private OrdemDeServicos fkOrdemServico;

}
