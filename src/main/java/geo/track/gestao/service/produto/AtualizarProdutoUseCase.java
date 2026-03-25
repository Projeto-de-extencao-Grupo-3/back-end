package geo.track.gestao.service.produto;

import geo.track.gestao.entity.Produto;
import geo.track.dto.produtos.ProdutoRequest;

public interface AtualizarProdutoUseCase {
    Produto execute(Integer id, ProdutoRequest request);
}

