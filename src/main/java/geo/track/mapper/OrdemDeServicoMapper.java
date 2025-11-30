package geo.track.mapper;

import geo.track.domain.OrdemDeServicos;
import geo.track.dto.os.response.OrdemDeServicoResponse;

import java.util.List;
import java.util.stream.Collectors;

public class OrdemDeServicoMapper {
    public static OrdemDeServicoResponse toResponse(OrdemDeServicos entity) {
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

        if (entity.getFk_entrada() != null) {
            response.setIdEntrada(entity.getFk_entrada().getIdRegistroEntrada());
        }

        return response;
    }

    public static List<OrdemDeServicoResponse> toResponse(List<OrdemDeServicos> entities) {
        return entities.stream()
                .map(OrdemDeServicoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
