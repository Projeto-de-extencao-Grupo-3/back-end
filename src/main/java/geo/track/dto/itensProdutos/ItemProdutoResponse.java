package geo.track.dto.itensProdutos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemProdutoResponse {
    private Integer idTransacaoProduto;
    //produto
    private String nomeProduto;
    private String fornecedorNf;
    private String precoCompra;
    private String precoVenda;
    private Boolean visivelOrcamentoCliente;
    private Integer quantidade;
    private Double precoPeca;
    private Boolean baixado;
    private String tipoServico;
    private Boolean possivelRegistrarSaida;
    private Integer idProdutoEstoque;
}
