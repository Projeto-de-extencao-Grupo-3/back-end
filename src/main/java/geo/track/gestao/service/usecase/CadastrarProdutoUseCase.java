package geo.track.gestao.service.usecase;

import geo.track.gestao.entity.ItemProduto;

public interface CadastrarProdutoUseCase {
    ItemProduto execute(ItemProduto entrada);
}
