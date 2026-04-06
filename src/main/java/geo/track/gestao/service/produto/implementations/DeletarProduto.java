package geo.track.gestao.service.produto.implementations;

import geo.track.gestao.entity.Produto;
import geo.track.gestao.entity.repository.ProdutoRepository;
import geo.track.gestao.service.ProdutoService;
import geo.track.gestao.service.produto.DeletarProdutoUseCase;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ProdutoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarProduto implements DeletarProdutoUseCase {
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final ProdutoService PRODUTO_SERVICE;
    private final Log log;

    public void execute(Integer id) {
        Produto produto = PRODUTO_SERVICE.buscarProdutosPorId(id);

        log.info("Excluindo produto ID: {}", id);
        produto.setAtivo(false);
        PRODUTO_REPOSITORY.save(produto);
    }
}

