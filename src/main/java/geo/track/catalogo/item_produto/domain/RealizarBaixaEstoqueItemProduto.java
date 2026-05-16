package geo.track.catalogo.item_produto.domain;

import geo.track.catalogo.item_produto.infraestructure.persistence.entity.ItemProduto;
import geo.track.catalogo.produto.domain.ProdutoService;
import geo.track.catalogo.item_produto.infraestructure.persistence.ItemProdutoRepository;
import geo.track.catalogo.item_produto.application.RealizarBaixaEstoqueItemProdutoUseCase;
import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import geo.track.catalogo.produto.infraestructure.persistence.entity.Produto;
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
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final ItemProdutoService ITEM_PRODUTO_SERVICE;
    private final ProdutoService PRODUTO_SERVICE;
    private final Log log;

    @Override
    public Boolean execute(Integer id, Integer quantidade, Integer tela) {
        Produto produto;
        ItemProduto itemProduto = null;

        if (tela == 1) {
            itemProduto = ITEM_PRODUTO_SERVICE.buscarRegistroPorId(id);
            produto = itemProduto.getFkProduto();
        } else {
            produto = PRODUTO_SERVICE.buscarProdutosPorId(id);
        }

        Integer quantidadeEfetiva = (quantidade != null)
                ? quantidade
                : (itemProduto != null ? itemProduto.getQuantidade() : 0);

        if (quantidadeEfetiva <= 0) {
            throw new BadRequestException("Quantidade inválida", Domains.ITEM_PRODUTO);
        }
        Integer quantidadeEstoque = produto.getQuantidadeEstoque();

        if (tela == 1) {
            if (itemProduto.getBaixado()) {
                throw new BadRequestException("Item já baixado", Domains.ITEM_PRODUTO);
            }

            if (quantidadeEstoque < quantidadeEfetiva) {
                throw new BadBusinessRuleException("Estoque insuficiente", Domains.ITEM_PRODUTO);
            }
            produto.setQuantidadeEstoque(quantidadeEstoque - quantidadeEfetiva);

            if (quantidadeEfetiva.equals(itemProduto.getQuantidade())) {
                itemProduto.setBaixado(true);
            } else {
                itemProduto.setBaixado(true);
                itemProduto.setQuantidade(quantidadeEfetiva);
            }
            ITEM_PRODUTO_REPOSITORY.save(itemProduto);
        } else {
            produto.setQuantidadeEstoque(quantidadeEstoque + quantidadeEfetiva);
        }
        PRODUTO_REPOSITORY.save(produto);

        return true;
    }
}

