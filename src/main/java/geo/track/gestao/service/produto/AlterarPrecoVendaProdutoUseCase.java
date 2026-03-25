package geo.track.gestao.service.produto;

import geo.track.gestao.entity.Produto;
import geo.track.dto.produtos.RequestPatchPrecoVenda;

public interface AlterarPrecoVendaProdutoUseCase {
    Produto execute(RequestPatchPrecoVenda request);
}

