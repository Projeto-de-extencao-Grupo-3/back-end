package geo.track.gestao.service.produto.implementations;

import geo.track.gestao.entity.ItemProduto;
import geo.track.gestao.entity.repository.ItemProdutoRepository;
import geo.track.gestao.service.ItemProdutoService;
import geo.track.gestao.service.produto.DeletarItemProdutoUseCase;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarItemProduto implements DeletarItemProdutoUseCase {
    private final ItemProdutoService ITEM_PRODUTO_SERVICE;
    private final ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;
    private final Log log;

    public void execute(Integer id) {
        log.info("Deletando item de produto ID: {}", id);
        ItemProduto item = ITEM_PRODUTO_SERVICE.buscarRegistroPorID(id);
        ITEM_PRODUTO_REPOSITORY.delete(item);
    }
}

