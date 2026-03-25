package geo.track.gestao.service.produto.implementations;

import geo.track.gestao.entity.Produto;
import geo.track.gestao.entity.repository.ProdutoRepository;
import geo.track.gestao.service.produto.AlterarQuantidadeEstoqueProdutoUseCase;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ProdutoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlterarQuantidadeEstoqueProdutoImplementation implements AlterarQuantidadeEstoqueProdutoUseCase {
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final Log log;

    public Produto execute(RequestPatchQtdEstoque body) {
        Optional<Produto> produtoOpt = PRODUTO_REPOSITORY.findByIdProdutoAndAtivoTrue(body.getId());

        if (produtoOpt.isEmpty()) {
            log.error("Falha ao atualizar estoque: Produto ID {} nao encontrado", body.getId());
            throw new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, Domains.PRODUTO);
        }

        Produto prod = produtoOpt.get();
        prod.setQuantidadeEstoque(body.getQuantidadeEstoque());
        log.info("Quantidade em estoque atualizada para o produto ID: {}. Nova quantidade: {}", prod.getIdProduto(), prod.getQuantidadeEstoque());
        return PRODUTO_REPOSITORY.save(prod);
    }
}

