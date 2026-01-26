package geo.track.mapper;

import geo.track.domain.ItemProduto;
import geo.track.dto.itensProdutos.ItemProdutoOsResponse;
import geo.track.dto.itensProdutos.ItemProdutoResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ItemProdutoMapper {
    public static ItemProdutoResponse toResponse(ItemProduto entity) {
        if (entity == null) {
            return null;
        }

        ItemProdutoResponse response = new ItemProdutoResponse();
        response.setIdItensProdutos(entity.getIdRegistroPeca());
        response.setQuantidade(entity.getQuantidade());
        response.setPrecoPeca(entity.getPrecoPeca());
        response.setBaixado(entity.getBaixado());

        if (entity.getFkPeca() != null) {
            response.setIdPeca(entity.getFkPeca().getIdProduto());
        }

        if (entity.getFkOrdemServico() != null) {
            response.setIdOrdemServico(entity.getFkOrdemServico().getIdOrdemServico());
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
}
