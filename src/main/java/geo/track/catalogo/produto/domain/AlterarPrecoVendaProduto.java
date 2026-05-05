package geo.track.catalogo.produto.domain;

import geo.track.catalogo.produto.infraestructure.persistence.entity.Produto;
import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import geo.track.catalogo.produto.application.AlterarPrecoVendaProdutoUseCase;
import geo.track.catalogo.produto.infraestructure.request.RequestPatchPrecoVenda;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlterarPrecoVendaProduto implements AlterarPrecoVendaProdutoUseCase {
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final ProdutoService PRODUTO_SERVICE;
    private final Log log;

    public Produto execute(RequestPatchPrecoVenda body) {
        Produto prod = PRODUTO_SERVICE.buscarProdutosPorId(body.getId());
        prod.setPrecoVenda(body.getPrecoVenda());

        log.info("Preco de venda atualizado para o produto ID: {}", prod.getIdProduto());
        return PRODUTO_REPOSITORY.save(prod);
    }
}

