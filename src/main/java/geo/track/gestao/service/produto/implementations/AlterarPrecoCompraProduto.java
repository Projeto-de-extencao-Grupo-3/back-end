package geo.track.gestao.service.produto.implementations;

import geo.track.gestao.entity.Produto;
import geo.track.gestao.entity.repository.ProdutoRepository;
import geo.track.gestao.service.ProdutoService;
import geo.track.gestao.service.produto.AlterarPrecoCompraProdutoUseCase;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ProdutoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

