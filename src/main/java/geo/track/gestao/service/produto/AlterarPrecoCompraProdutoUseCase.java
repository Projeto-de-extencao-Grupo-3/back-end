package geo.track.gestao.service.produto;

import geo.track.gestao.entity.Produto;
import geo.track.dto.produtos.RequestPatchPrecoCompra;

public interface AlterarPrecoCompraProdutoUseCase {
    Produto execute(RequestPatchPrecoCompra request);
}

