package geo.track.mapper;

import geo.track.domain.OrdemDeServico;
import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.dto.os.response.OrdemDeServicoResponse;

import java.util.List;
import java.util.stream.Collectors;

public class OrdemDeServicoMapper {
    public static OrdemDeServicoResponse toResponse(OrdemDeServico entity) {
        if (entity == null) {
            return null;
        }

        OrdemDeServicoResponse response = new OrdemDeServicoResponse();
        response.setIdOrdemServico(entity.getIdOrdemServico());
        response.setValorTotal(entity.getValorTotal());
        response.setDtSaidaPrevista(entity.getDtSaidaPrevista());
        response.setDtSaidaEfetiva(entity.getDtSaidaEfetiva());
        response.setStatus(entity.getStatus());
        response.setSeguradora(entity.getSeguradora());
        response.setNfRealizada(entity.getNfRealizada());
        response.setPagtRealizado(entity.getPagtRealizado());
        response.setServicos(ItemServicoMapper.toResponse(entity.getServicos()));
        response.setProdutos(ItemProdutoMapper.toResponse(entity.getProdutos()));

        if (entity.getFk_entrada() != null) {
            response.setIdEntrada(entity.getFk_entrada().getIdRegistroEntrada());
        }

        return response;
    }

    public static List<OrdemDeServicoResponse> toResponse(List<OrdemDeServico> entities) {
        return entities.stream()
                .map(OrdemDeServicoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
