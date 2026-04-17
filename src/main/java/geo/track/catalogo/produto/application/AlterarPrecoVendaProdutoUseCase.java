package geo.track.catalogo.produto.application;

import geo.track.catalogo.produto.domain.entity.Produto;
import geo.track.catalogo.produto.infraestructure.request.RequestPatchPrecoVenda;

public interface AlterarPrecoVendaProdutoUseCase {
    Produto execute(RequestPatchPrecoVenda request);
}

