package geo.track.catalogo.item_produto.infraestructure.response;

import lombok.Data;

@Data
public class ItemProdutoOsResponse {
    private Integer idRegistroPeca;
    private Integer quantidade;
    private Double precoPeca;
    private Boolean baixado;

    // Campos de Produto
    private String nomeProduto;
    private Boolean visivelOrcamento;
    private Double precoCompra;
    private Double precoVenda;
}
