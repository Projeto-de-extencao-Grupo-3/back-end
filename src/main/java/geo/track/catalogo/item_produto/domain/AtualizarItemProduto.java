package geo.track.catalogo.item_produto.domain;

import geo.track.catalogo.item_produto.infraestructure.persistence.entity.ItemProduto;
import geo.track.catalogo.item_produto.infraestructure.request.RequestPutItemProduto;
import geo.track.catalogo.item_produto.infraestructure.persistence.ItemProdutoRepository;
import geo.track.catalogo.item_produto.application.AtualizarItemProdutoUseCase;
import geo.track.catalogo.item_produto.infraestructure.ItemProdutoMapper;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtualizarItemProduto implements AtualizarItemProdutoUseCase {
    private final ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;
    private final ItemProdutoService ITEM_PRODUTO_SERVICE;
    private final Log log;

    public ItemProduto execute(Integer id, RequestPutItemProduto body) {
        log.info("Iniciando atualizacao do item de produto ID: {}", id);
        ItemProduto registroDesejado = ITEM_PRODUTO_SERVICE.buscarRegistroPorId(id);

        log.info("Mapeando alteracoes para o item ID: {}", id);
        ItemProduto registroAtualizado = ItemProdutoMapper.updateEntity(registroDesejado, body);

        ItemProduto atualizado = ITEM_PRODUTO_REPOSITORY.save(registroAtualizado);
        log.info("Item de produto ID: {} atualizado com sucesso.", id);
        return atualizado;
    }
}

