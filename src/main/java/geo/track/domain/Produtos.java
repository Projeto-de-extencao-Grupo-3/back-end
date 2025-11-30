package geo.track.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da peça", example = "1")
    private Integer idProduto;

    @NotBlank
    @Schema(description = "Nome da peça ou produto", example = "Filtro de óleo")
    private String nome;

    @NotBlank
    @Schema(description = "Número da nota fiscal do fornecedor", example = "NF-987654")
    private String fornecedorNf;

    @NotNull
    @Positive
    @Schema(description = "Preço de compra do produto", example = "120.50")
    private Double precoCompra;

    @NotNull
    @Positive
    @Schema(description = "Preço de venda do produto", example = "180.00")
    private Double precoVenda;

    @NotNull
    @Positive
    @Schema(description = "Quantidade disponível em estoque", example = "45")
    private Integer quantidadeEstoque;

    @NotNull
    @Positive
    @Schema(description = "Visibilidade de item em Orçamento", example = "45")
    private Boolean viavelOrcamento;

    @OneToMany(mappedBy = "fkPeca")
    @Schema(description = "Lista de produtos associados à uma lista de produtos")
    private List<ItensProdutos> produtos;
}

