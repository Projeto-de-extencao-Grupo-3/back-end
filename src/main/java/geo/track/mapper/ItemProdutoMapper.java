package geo.track.mapper;

import geo.track.domain.ItemProduto;
import geo.track.domain.OrdemDeServico;
import geo.track.domain.Produto;
import geo.track.dto.itensProdutos.ItemProdutoOsResponse;
import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensProdutos.RequestPutItemProduto;

import java.util.List;
import java.util.stream.Collectors;

public class ItemProdutoMapper {
    public static ItemProdutoResponse toResponse(ItemProduto entity) {
        if (entity == null) {
            return null;
        }

        ItemProdutoResponse response = new ItemProdutoResponse();
        response.setQuantidade(entity.getQuantidade());
        response.setPrecoPeca(entity.getPrecoPeca());
        response.setBaixado(entity.getBaixado());

        if (entity.getFkPeca() != null) {
            response.setIdPeca(entity.getFkPeca().getIdProduto());
        }

        return response;
    }

    public static List<ItemProdutoResponse> toResponse(List<ItemProduto> entities) {
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

        if (entity.getFkPeca() != null) {
            response.setNomeProduto(entity.getFkPeca().getNome());
            response.setViavelOrcamento(entity.getFkPeca().getViavelOrcamento());
            response.setPrecoCompra(entity.getFkPeca().getPrecoCompra());
            response.setPrecoVenda(entity.getFkPeca().getPrecoVenda());
        }
        return response;
    }

    public static List<ItemProdutoOsResponse> toOsResponse(List<ItemProduto> entities) {
        return entities.stream()
                .map(ItemProdutoMapper::toOsResponse)
                .collect(Collectors.toList());
    }

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
