package geo.track.catalogo.item_produto.infraestructure;

import geo.track.catalogo.item_produto.infraestructure.persistence.entity.ItemProduto;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.catalogo.produto.domain.entity.Produto;
import geo.track.catalogo.item_produto.infraestructure.response.ItemProdutoOsResponse;
import geo.track.catalogo.item_produto.infraestructure.response.ItemProdutoResponse;
import geo.track.catalogo.item_produto.infraestructure.request.RequestPutItemProduto;
import geo.track.catalogo.item_produto.infraestructure.request.RequestPostItemProduto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemProdutoMapper {
    public static ItemProdutoResponse toResponse(ItemProduto entity) {
        if (entity == null) {
            return null;
        }


        ItemProdutoResponse response = new ItemProdutoResponse();
        response.setIdItemProduto(entity.getIdRegistroPeca());
        response.setNomeProduto(entity.getFkProduto().getNome());
        response.setFornecedorNf(entity.getFkProduto().getFornecedorNf());
        response.setPrecoCompra(entity.getFkProduto().getPrecoCompra().toString());
        response.setPrecoVenda(entity.getFkProduto().getPrecoVenda().toString());
        response.setVisivelOrcamentoCliente(entity.getFkProduto().getVisivelOrcamento());
        response.setQuantidade(entity.getQuantidade());
        response.setPrecoPeca(entity.getPrecoPeca());
        response.setBaixado(entity.getBaixado());
        response.setTipoServico(entity.getFkProduto().getTipoServico().toString());
        response.setPossivelRegistrarSaida(entity.possivelRealizarBaixaNoEstoque());
        response.setIdProdutoEstoque(entity.getFkProduto().getIdProduto());

        return response;
    }

    public static List<ItemProdutoResponse> toResponse(List<ItemProduto> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(ItemProdutoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static ItemProdutoOsResponse toOsResponse(ItemProduto entity) {
        if (entity == null) {
            return null;
        }

        ItemProdutoOsResponse response = new ItemProdutoOsResponse();
        response.setIdRegistroPeca(entity.getIdRegistroPeca());
        response.setQuantidade(entity.getQuantidade());
        response.setPrecoPeca(entity.getPrecoPeca());
        response.setBaixado(entity.getBaixado());

        Optional.ofNullable(entity.getFkProduto()).ifPresent(fkPeca -> {
            response.setNomeProduto(fkPeca.getNome());
            response.setVisivelOrcamento(fkPeca.getVisivelOrcamento());
            response.setPrecoCompra(fkPeca.getPrecoCompra());
            response.setPrecoVenda(fkPeca.getPrecoVenda());
        });
        return response;
    }

    public static List<ItemProdutoOsResponse> toOsResponse(List<ItemProduto> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(ItemProdutoMapper::toOsResponse)
                .collect(Collectors.toList());
    }

    public static ItemProduto toEntity(RequestPostItemProduto requestPostItemProduto, Produto produto, OrdemDeServico ordemServico) {
        ItemProduto itemProduto = new ItemProduto();

        itemProduto.setQuantidade(requestPostItemProduto.quantidade());
        itemProduto.setPrecoPeca(requestPostItemProduto.precoProduto());
        itemProduto.setBaixado(false);
        itemProduto.setFkProduto(produto);
        itemProduto.setFkOrdemServico(ordemServico);

        return itemProduto;
    }

    public static ItemProduto updateEntity(ItemProduto registroDesejado, RequestPutItemProduto body) {
        if (registroDesejado == null) {
            return null;    
        }

        if (body.quantidade() != null) {
            registroDesejado.setQuantidade(body.quantidade());
        }
        if (body.precoProduto() != null) {
            registroDesejado.setPrecoPeca(body.precoProduto());
        }
        if (body.baixado() != null) {
            registroDesejado.setBaixado(body.baixado());
        }

        return registroDesejado;
    }
}
