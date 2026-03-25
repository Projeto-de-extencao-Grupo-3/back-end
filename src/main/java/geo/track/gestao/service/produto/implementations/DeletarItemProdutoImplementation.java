package geo.track.gestao.service.produto.implementations;

import geo.track.gestao.entity.repository.ItemProdutoRepository;
import geo.track.gestao.service.produto.DeletarItemProdutoUseCase;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarItemProdutoImplementation implements DeletarItemProdutoUseCase {
    private final ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;
    private final Log log;

    public void execute(Integer id) {
        log.info("Deletando item de produto ID: {}", id);
        ITEM_PRODUTO_REPOSITORY.deleteById(id);
        log.info("Item de produto ID: {} deletado com sucesso.", id);
    }
}

