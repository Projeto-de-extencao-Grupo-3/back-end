package geo.track.mapper;

import geo.track.domain.ItemProduto;
import geo.track.domain.OrdemDeServico;
import geo.track.domain.Produto;
import geo.track.dto.itensProdutos.RequestPutItemProduto;

public class ItensProdutosMapper {

    public static ItemProduto toEntity(Integer id, Integer quantidade, Double precoProduto, Boolean baixado, Produto produto, OrdemDeServico ordemServico) {
        return new ItemProduto(
                null,
                quantidade,
                precoProduto,
                baixado,
                produto,
                ordemServico
        );
    }

    public static ItemProduto updateEntity(ItemProduto registroDesejado, RequestPutItemProduto body) {
        ItemProduto entity = registroDesejado;

        if (body.quantidade() != null) {
            entity.setQuantidade(body.quantidade());
        }
        if (body.precoProduto() != null) {
            entity.setPrecoPeca(body.precoProduto());
        }
        if (body.baixado() != null) {
            entity.setBaixado(body.baixado());
        }

        return entity;
    }
}
