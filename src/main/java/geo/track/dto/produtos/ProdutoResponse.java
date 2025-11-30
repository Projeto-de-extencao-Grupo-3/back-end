package geo.track.dto.produtos;

import lombok.Data;

@Data
public class ProdutoResponse {
    private Integer idPeca;
    private String nome;
    private String fornecedorNf;
    private Double precoCompra;
    private Double precoVenda;
    private Integer quantidadeEstoque;
}
