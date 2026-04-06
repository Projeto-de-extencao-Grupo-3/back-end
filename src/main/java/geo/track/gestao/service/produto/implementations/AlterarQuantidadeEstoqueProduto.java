package geo.track.gestao.service.produto.implementations;

import geo.track.gestao.entity.Produto;
import geo.track.gestao.entity.repository.ProdutoRepository;
import geo.track.gestao.service.ProdutoService;
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
public class AlterarQuantidadeEstoqueProduto implements AlterarQuantidadeEstoqueProdutoUseCase {
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final ProdutoService PRODUTO_SERVICE;
    private final Log log;

    public Produto execute(Integer id, Integer quantidadeEstoque) {
        Produto produto = PRODUTO_SERVICE.buscarProdutosPorId(id);
        produto.setQuantidadeEstoque(quantidadeEstoque);

        log.info("Quantidade em estoque atualizada para o produto ID: {}. Nova quantidade: {}", produto.getIdProduto(), produto.getQuantidadeEstoque());
        return PRODUTO_REPOSITORY.save(produto);
    }
}

