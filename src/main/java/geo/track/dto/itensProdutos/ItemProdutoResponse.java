package geo.track.dto.itensProdutos;

import lombok.Data;

@Data
public class ItemProdutoResponse {
    private Integer idItensProdutos;
    private Integer quantidade;
    private Double precoPeca;
    private Boolean baixado;
    private Integer idPeca;
    private Integer idOrdemServico;
}
