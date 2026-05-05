package geo.track.catalogo.produto.application;

import geo.track.catalogo.produto.infraestructure.persistence.entity.Produto;

public interface AlterarQuantidadeEstoqueProdutoUseCase {
    Produto execute(Integer idProduto, Integer quantidadeEstoque);
}

