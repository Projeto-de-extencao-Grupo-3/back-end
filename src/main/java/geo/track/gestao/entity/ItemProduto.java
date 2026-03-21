package geo.track.gestao.entity;

import geo.track.jornada.entity.OrdemDeServico;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
@Table(name = "itens_produtos")
public class ItemProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id da lista de produtos", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idRegistroPeca;

    @Schema(description = "Quantidade do produto selecionado", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantidade;

    @Schema(description = "Preço da peça selecionada", example = "29.90", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double precoPeca;

    @Schema(description = "Indicador se o item foi abaixado no Estoque", example = "29.90", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean baixado;

    @ManyToOne
    @JoinColumn(name = "fk_produto")
    @Schema(description = "FK do produto")
    private Produto fkProduto;

    @ManyToOne
    @JoinColumn(name = "fk_ordem_servico")
    @Schema(description = "FK da ordem de serviço ")
    private OrdemDeServico fkOrdemServico;

    public Boolean possivelRealizarBaixaNoEstoque() {
        return this.fkProduto.getQuantidadeEstoque() >= this.quantidade;
    }
}
