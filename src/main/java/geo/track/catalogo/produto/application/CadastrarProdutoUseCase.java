package geo.track.catalogo.produto.application;

import geo.track.catalogo.produto.infraestructure.persistence.entity.Produto;
import geo.track.catalogo.produto.infraestructure.request.ProdutoRequest;

public interface CadastrarProdutoUseCase {
    Produto execute(ProdutoRequest request);
}
