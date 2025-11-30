package geo.track.mapper;

import geo.track.domain.ItensProdutos;
import geo.track.domain.OrdemDeServicos;
import geo.track.domain.Produtos;
import geo.track.dto.itensProdutos.RequestPostItemProduto;
import geo.track.dto.itensProdutos.RequestPutItemProduto;

public class ItensProdutosMapper {

    public static ItensProdutos toEntity(Integer id, Integer quantidade, Double precoProduto, Boolean baixado, Produtos produto, OrdemDeServicos ordemServico) {
        return new ItensProdutos(
                null,
                quantidade,
                precoProduto,
                baixado,
                produto,
                ordemServico
        );
    }

    public static ItensProdutos updateEntity(ItensProdutos registroDesejado, RequestPutItemProduto body) {
        ItensProdutos entity = registroDesejado;

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
