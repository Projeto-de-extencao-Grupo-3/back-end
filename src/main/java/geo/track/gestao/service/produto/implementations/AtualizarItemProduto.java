package geo.track.gestao.service.produto.implementations;

import geo.track.gestao.entity.ItemProduto;
import geo.track.dto.itensProdutos.RequestPutItemProduto;
import geo.track.gestao.entity.repository.ItemProdutoRepository;
import geo.track.gestao.service.produto.AtualizarItemProdutoUseCase;
import geo.track.gestao.util.ItemProdutoMapper;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ItemProdutoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtualizarItemProduto implements AtualizarItemProdutoUseCase {
    private final ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;
    private final Log log;

    public ItemProduto execute(Integer id, RequestPutItemProduto body) {
        log.info("Iniciando atualizacao do item de produto ID: {}", id);
        ItemProduto registroDesejado = ITEM_PRODUTO_REPOSITORY.findById(id).orElseThrow(() -> new DataNotFoundException(ItemProdutoExceptionMessages.ITEM_PRODUTO_NAO_ENCONTRADO, Domains.ITEM_SERVICO));

        log.info("Mapeando alteracoes para o item ID: {}", id);
        ItemProduto registroAtualizado = ItemProdutoMapper.updateEntity(registroDesejado, body);

        ItemProduto atualizado = ITEM_PRODUTO_REPOSITORY.save(registroAtualizado);
        log.info("Item de produto ID: {} atualizado com sucesso.", id);
        return atualizado;
    }
}

