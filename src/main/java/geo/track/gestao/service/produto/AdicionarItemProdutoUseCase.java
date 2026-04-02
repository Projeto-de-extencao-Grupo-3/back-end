package geo.track.gestao.service.produto;

import geo.track.gestao.entity.ItemProduto;
import geo.track.jornada.request.itens.RequestPostItemProduto;

public interface AdicionarItemProdutoUseCase {
    ItemProduto execute(Integer id, RequestPostItemProduto request);
}

