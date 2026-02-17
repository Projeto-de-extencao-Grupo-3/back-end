package geo.track.dto.itensProdutos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemProdutoResponse {
    private Integer idItensProdutos;
    private Integer quantidade;
    private Double precoPeca;
    private Boolean baixado;
    private Integer idPeca;
    private Integer idOrdemServico;
}
