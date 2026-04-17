package geo.track.catalogo.produto.domain;

import geo.track.catalogo.produto.domain.entity.Produto;
import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import geo.track.catalogo.produto.application.AlterarPrecoCompraProdutoUseCase;
import geo.track.catalogo.produto.infraestructure.request.RequestPatchPrecoCompra;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlterarPrecoCompraProduto implements AlterarPrecoCompraProdutoUseCase {
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final ProdutoService PRODUTO_SERVICE;
    private final Log log;

    public Produto execute(RequestPatchPrecoCompra body) {
        Produto produto = PRODUTO_SERVICE.buscarProdutosPorId(body.getId());

        produto.setPrecoCompra(body.getPrecoCompra());
        log.info("Preco de compra atualizado para o produto ID: {}", produto.getIdProduto());
        return PRODUTO_REPOSITORY.save(produto);
    }
}

