package geo.track.gestao.service;

import geo.track.gestao.entity.ItemProduto;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ItemProdutoExceptionMessages;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.entity.repository.ItemProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemProdutoService {
    private final ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;
    private final Log log;

    public List<ItemProduto> listarRegistros() {
        log.info("Listando todos os registros de itens de produtos.");
        return ITEM_PRODUTO_REPOSITORY.findAll();
    }

    public ItemProduto buscarRegistroPorId(Integer id) {
        log.info("Buscando item de produto pelo ID: {}", id);
        ItemProduto registroProduto = ITEM_PRODUTO_REPOSITORY.findById(id).orElseThrow(() -> new DataNotFoundException(ItemProdutoExceptionMessages.ITEM_PRODUTO_NAO_ENCONTRADO, Domains.ITEM_SERVICO));
        return registroProduto;
    }

}
