package geo.track.mapper;

import geo.track.domain.ItemProduto;
import geo.track.domain.OrdemDeServico;
import geo.track.domain.Produto;
import geo.track.dto.itensProdutos.ItemProdutoOsResponse;
import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensProdutos.RequestPutItemProduto;

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
        response.setQuantidade(entity.getQuantidade());
        response.setPrecoPeca(entity.getPrecoPeca());
        response.setBaixado(entity.getBaixado());
        response.setVisivelOrcamento(entity.getFkPeca().getVisivelOrcamento());

        Optional.ofNullable(entity.getFkPeca())
                .map(Produto::getIdProduto)
                .ifPresent(response::setIdPeca);

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

        // Safe access to nested properties
        Optional.ofNullable(entity.getFkPeca()).ifPresent(fkPeca -> {
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
        if (registroDesejado == null) {
            return null; // Or throw an IllegalArgumentException
        }

        // Assuming body is not null and its fields are handled by validation or are nullable wrappers
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
