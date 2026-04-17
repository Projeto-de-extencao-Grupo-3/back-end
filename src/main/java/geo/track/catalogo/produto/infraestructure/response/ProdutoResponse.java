package geo.track.catalogo.produto.infraestructure.response;

import lombok.Data;

@Data
public class ProdutoResponse {
    private Integer idPeca;
    private String nome;
    private String fornecedorNf;
    private Double precoCompra;
    private Double precoVenda;
    private Boolean visivelOrcamento;
    private String tipoServico;
    private Integer quantidadeEstoque;
}
