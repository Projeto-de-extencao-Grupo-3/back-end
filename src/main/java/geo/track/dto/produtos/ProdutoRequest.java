package geo.track.dto.produtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import geo.track.enums.Servico;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequest {
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
    @Schema(description = "Visibilidade de item em Orçamento", example = "true")
    private Boolean visivelOrcamento;

    @Schema(description = "Tipo serviço", example = "PINTURA")
    private Servico tipoServico;
}
