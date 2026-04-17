package geo.track.catalogo.item_produto.application;

import geo.track.catalogo.item_produto.infraestructure.persistence.entity.ItemProduto;
import geo.track.catalogo.item_produto.infraestructure.request.RequestPutItemProduto;

public interface AtualizarItemProdutoUseCase {
    ItemProduto execute(Integer id, RequestPutItemProduto request);
}

