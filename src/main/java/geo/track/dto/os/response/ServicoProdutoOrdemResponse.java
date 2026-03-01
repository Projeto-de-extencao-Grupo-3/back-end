package geo.track.dto.os.response;

import geo.track.domain.ItemProduto;
import geo.track.domain.ItemServico;
import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensServicos.ItemServicoResponse;

import java.util.List;

public record ServicoProdutoOrdemResponse(
        List<ItemServicoResponse> servicos,
        List<ItemProdutoResponse> produtos
) {
}
