package geo.track.catalogo.item_produto.domain;

import geo.track.catalogo.item_produto.infraestructure.persistence.entity.ItemProduto;
import geo.track.catalogo.item_produto.infraestructure.persistence.ItemProdutoRepository;
import geo.track.catalogo.item_produto.application.DeletarItemProdutoUseCase;
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
        ItemProduto item = ITEM_PRODUTO_SERVICE.buscarRegistroPorId(id);
        ITEM_PRODUTO_REPOSITORY.delete(item);
    }
}