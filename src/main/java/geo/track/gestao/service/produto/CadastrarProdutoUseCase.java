package geo.track.gestao.service.produto;

import geo.track.gestao.entity.Produto;
import geo.track.dto.produtos.ProdutoRequest;

public interface CadastrarProdutoUseCase {
    Produto execute(ProdutoRequest request);
}
