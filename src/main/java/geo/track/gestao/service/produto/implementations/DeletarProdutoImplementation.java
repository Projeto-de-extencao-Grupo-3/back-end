package geo.track.gestao.service.produto.implementations;

import geo.track.gestao.entity.Produto;
import geo.track.gestao.entity.repository.ProdutoRepository;
import geo.track.gestao.service.produto.DeletarProdutoUseCase;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ProdutoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarProdutoImplementation implements DeletarProdutoUseCase {
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final Log log;

    public void execute(Integer id) {
        if (!PRODUTO_REPOSITORY.existsByIdProdutoAndAtivoTrue(id)) {
            log.error("Falha ao excluir: Produto ID {} nao encontrado", id);
            throw new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, Domains.PRODUTO);
        }
        Produto produto = PRODUTO_REPOSITORY.findById(id).get();

        log.info("Excluindo produto ID: {}", id);
        produto.setAtivo(false);
        PRODUTO_REPOSITORY.save(produto);
    }
}

