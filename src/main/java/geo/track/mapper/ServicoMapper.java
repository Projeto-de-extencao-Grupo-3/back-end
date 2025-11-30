package geo.track.mapper;

import geo.track.domain.Servicos;
import geo.track.dto.servicos.ServicoResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ServicoMapper {
    public static ServicoResponse toResponse(Servicos entity) {
        if (entity == null) {
            return null;
        }

        ServicoResponse response = new ServicoResponse();
        response.setIdServico(entity.getIdServico());
        response.setTipoServico(entity.getTipoServico());
        response.setTituloServico(entity.getTituloServico());
        response.setTempoBase(entity.getTempoBase());
        response.setAtivo(entity.isAtivo());

        return response;
    }

    public static List<ServicoResponse> toResponse(List<Servicos> entities) {
        return entities.stream()
                .map(ServicoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
