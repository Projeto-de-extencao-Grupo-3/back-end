package geo.track.catalogo.produto.application;

import geo.track.catalogo.produto.domain.entity.Produto;
import geo.track.catalogo.produto.infraestructure.request.ProdutoRequest;

public interface AtualizarProdutoUseCase {
    Produto execute(Integer id, ProdutoRequest request);
}

