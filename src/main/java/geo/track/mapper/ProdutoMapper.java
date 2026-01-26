package geo.track.mapper;

import geo.track.domain.Produto;
import geo.track.dto.produtos.ProdutoResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoMapper {
    public static ProdutoResponse toResponse(Produto entity) {
        if (entity == null) {
            return null;
        }

        ProdutoResponse response = new ProdutoResponse();
        response.setIdPeca(entity.getIdProduto());
        response.setNome(entity.getNome());
        response.setFornecedorNf(entity.getFornecedorNf());
        response.setPrecoCompra(entity.getPrecoCompra());
        response.setPrecoVenda(entity.getPrecoVenda());
        response.setQuantidadeEstoque(entity.getQuantidadeEstoque());

        return response;
    }

    public static List<ProdutoResponse> toResponse(List<Produto> entities) {
        return entities.stream()
                .map(ProdutoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
