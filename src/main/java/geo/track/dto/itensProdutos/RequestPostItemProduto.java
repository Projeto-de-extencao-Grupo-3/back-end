package geo.track.dto.itensProdutos;

public record RequestPostItemProduto(
        Integer fkOrdemServico,
        Integer fkProduto,
        Integer quantidade,
        Double precoProduto
) {
}
