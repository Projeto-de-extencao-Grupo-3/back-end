package geo.track.jornada.service.itens;

import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.infraestructure.exception.BadRequestException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.gestao.entity.ItemProduto;
import geo.track.gestao.entity.Produto;
import geo.track.gestao.entity.repository.ItemProdutoRepository;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.service.ItemProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Strategy para ADICIONAR SERVICO AO ORÇAMENTO
 **/
@Component
@RequiredArgsConstructor
public class RealizarSaidaMaterialStrategy implements ItensJornadaStrategy {
    private final ItemProdutoService ITEM_PRODUTO_SERVICE;
    private final ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.SAIDA_MATERIAL.equals(tipoJornada);
    }

    @Override
    public ItemProduto execute(Integer idItemProduto, GetJornada getRequest) {
        ItemProduto itemProduto = ITEM_PRODUTO_SERVICE.buscarRegistroPorID(idItemProduto);
        Produto produto = itemProduto.getFkProduto();

        Integer quantidadeDesejada = itemProduto.getQuantidade();
        Integer quantidadeEstoque = produto.getQuantidadeEstoque();

        if (itemProduto.getBaixado()) throw new BadRequestException("Este item já foi baixado.", Domains.ITEM_PRODUTO);
        if (quantidadeEstoque < quantidadeDesejada) throw new BadBusinessRuleException("Quantidade solicitada excede o estoque disponível.", Domains.ITEM_PRODUTO);

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeDesejada);
        itemProduto.setBaixado(true);
        itemProduto.setFkProduto(produto);

        return ITEM_PRODUTO_REPOSITORY.save(itemProduto);
    }
}
