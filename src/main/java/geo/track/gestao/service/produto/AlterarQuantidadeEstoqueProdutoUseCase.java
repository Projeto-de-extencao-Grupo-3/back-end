package geo.track.gestao.service.produto;

import geo.track.gestao.entity.Produto;
import geo.track.dto.produtos.RequestPatchQtdEstoque;

public interface AlterarQuantidadeEstoqueProdutoUseCase {
    Produto execute(Integer idProduto, Integer quantidadeEstoque);
}

