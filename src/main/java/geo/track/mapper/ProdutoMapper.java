package geo.track.mapper;

import geo.track.domain.Produto;
import geo.track.dto.produtos.ProdutoRequest;
import geo.track.dto.produtos.ProdutoResponse;
import geo.track.enums.Servico;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
        response.setVisivelOrcamento(entity.getVisivelOrcamento());
        response.setTipoServico(entity.getTipoServico().name());

        return response;
    }

    public static HashMap<String, List<ProdutoResponse>> toResponse(HashMap<String, List<Produto>> entities) {
        if (entities == null) {
            return null;
        }

        HashMap<String, List<ProdutoResponse>> response = new HashMap<>();
        List<String> tipoServicos = Arrays.stream(Servico.values()).map(Servico::name).toList();

        tipoServicos.forEach(t -> {
            response.put(t, ProdutoMapper.toResponse(entities.get(t)));
        });

        if(response.isEmpty()){
            return null;
        }

        return response;
    }


    public static List<ProdutoResponse> toResponse(List<Produto> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(ProdutoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static Produto toEntity(ProdutoRequest request) {
        if (request == null) {
            return null;
        }

        Produto entity = new Produto();
        entity.setNome(request.getNome());
        entity.setFornecedorNf(request.getFornecedorNf());
        entity.setPrecoCompra(request.getPrecoCompra());
        entity.setPrecoVenda(request.getPrecoVenda());
        entity.setQuantidadeEstoque(request.getQuantidadeEstoque());
        entity.setVisivelOrcamento(request.getVisivelOrcamento());
        entity.setTipoServico(request.getTipoServico());

        return entity;
    }
}
