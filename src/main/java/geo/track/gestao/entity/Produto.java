package geo.track.gestao.entity;

import geo.track.enums.Servico;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da peça", example = "1")
    private Integer idProduto;

    @Schema(description = "Nome da peça ou produto", example = "Filtro de óleo")
    private String nome;

    @Schema(description = "Número da nota fiscal do fornecedor", example = "NF-987654")
    private String fornecedorNf;

    @Schema(description = "Preço de compra do produto", example = "120.50")
    private Double precoCompra;

    @Schema(description = "Preço de venda do produto", example = "180.00")
    private Double precoVenda;

    @Schema(description = "Quantidade disponível em estoque", example = "45")
    private Integer quantidadeEstoque;

    @Schema(description = "Visibilidade de item em Orçamento", example = "45")
    private Boolean visivelOrcamento;

    @OneToMany(mappedBy = "fkProduto")
    @Schema(description = "Lista de produtos associados à uma lista de produtos")
    private List<ItemProduto> produtos;

    @Schema(description = "Tipo serviço", example = "PINTURA")
    @Enumerated(EnumType.STRING)
    private Servico tipoServico;

    @Schema(description = "Ativo ou inativo", example = "True")
    private Boolean ativo;
}
