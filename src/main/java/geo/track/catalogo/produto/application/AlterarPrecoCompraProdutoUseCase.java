package geo.track.catalogo.produto.application;

import geo.track.catalogo.produto.domain.entity.Produto;
import geo.track.catalogo.produto.infraestructure.request.RequestPatchPrecoCompra;

public interface AlterarPrecoCompraProdutoUseCase {
    Produto execute(RequestPatchPrecoCompra request);
}

