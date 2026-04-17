package geo.track.catalogo.item_produto.application;

import geo.track.catalogo.item_produto.infraestructure.persistence.entity.ItemProduto;
import geo.track.catalogo.item_produto.infraestructure.request.RequestPostItemProduto;

public interface AdicionarItemProdutoUseCase {
    ItemProduto execute(Integer id, RequestPostItemProduto request);
}

