package geo.track.dto.itensProdutos;

import lombok.Data;

@Data
public class ItemProdutoOsResponse {
    private Integer idRegistroPeca;
    private Integer quantidade;
    private Double precoPeca;
    private Boolean baixado;

    // Campos de Produto
    private String nomeProduto;
    private Boolean viavelOrcamento;
    private Double precoCompra;
    private Double precoVenda;
}
