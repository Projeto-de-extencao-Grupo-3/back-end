package geo.track.dto.itensprodutos;

import lombok.Data;

@Data
public class ItensProdutoResponse {
    private Integer idItensProdutos;
    private Integer quantidade;
    private Double precoPeca;
    private Boolean baixado;
    private Integer idPeca;
    private Integer idOrdemServico;
}
