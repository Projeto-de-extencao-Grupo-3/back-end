package geo.track.mapper;

import geo.track.domain.ItensProdutos;
import geo.track.dto.itensprodutos.ItensProdutoResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ItensProdutoMapper {
    public static ItensProdutoResponse toResponse(ItensProdutos entity) {
        if (entity == null) {
            return null;
        }

        ItensProdutoResponse response = new ItensProdutoResponse();
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

    public static List<ItensProdutoResponse> toResponse(List<ItensProdutos> entities) {
        return entities.stream()
                .map(ItensProdutoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
