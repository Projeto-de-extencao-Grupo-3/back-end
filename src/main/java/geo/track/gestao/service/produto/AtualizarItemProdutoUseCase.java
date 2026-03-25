package geo.track.gestao.service.produto;

import geo.track.gestao.entity.ItemProduto;
import geo.track.dto.itensProdutos.RequestPutItemProduto;

public interface AtualizarItemProdutoUseCase {
    ItemProduto execute(Integer id, RequestPutItemProduto request);
}

