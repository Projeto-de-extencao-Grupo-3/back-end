package geo.track.gestao.service.usecase;

import geo.track.gestao.entity.Produto;

public interface CadastrarProdutoCatalogoUseCase {
    Produto execute(Produto entrada);
}
