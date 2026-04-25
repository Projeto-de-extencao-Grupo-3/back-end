package geo.track.catalogo.item_produto.domain;

import geo.track.catalogo.item_produto.infraestructure.persistence.entity.ItemProduto;
import geo.track.catalogo.produto.infraestructure.persistence.entity.Produto;
import geo.track.catalogo.item_produto.infraestructure.persistence.ItemProdutoRepository;
import geo.track.catalogo.item_produto.application.RealizarBaixaEstoqueItemProdutoUseCase;
import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.infraestructure.exception.BadRequestException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealizarBaixaEstoqueItemProduto implements RealizarBaixaEstoqueItemProdutoUseCase {
    private final ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;
    private final ItemProdutoService ITEM_PRODUTO_SERVICE;
    private final Log log;

    public Boolean execute(Integer id) {
        log.info("Iniciando processo de baixa de estoque para o item de produto ID: {}", id);
        ItemProduto itemProduto = ITEM_PRODUTO_SERVICE.buscarRegistroPorId(id);
        Produto produto = itemProduto.getFkProduto();

        Integer quantidadeDesejada = itemProduto.getQuantidade();
        Integer quantidadeEstoque = produto.getQuantidadeEstoque();

        if (itemProduto.getBaixado()) throw new BadRequestException("Este item já foi baixado.", Domains.ITEM_PRODUTO);
        if (quantidadeEstoque < quantidadeDesejada) throw new BadBusinessRuleException("Quantidade solicitada excede o estoque disponível.", Domains.ITEM_PRODUTO);

        log.info("Reduzindo {} unidades do produto ID: {}", quantidadeDesejada, produto.getIdProduto());
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeDesejada);
        itemProduto.setBaixado(true);
        itemProduto.setFkProduto(produto);
        ITEM_PRODUTO_REPOSITORY.save(itemProduto);

        log.info("Baixa de estoque realizada com sucesso para o item ID: {}", id);
        return true;
    }
}

