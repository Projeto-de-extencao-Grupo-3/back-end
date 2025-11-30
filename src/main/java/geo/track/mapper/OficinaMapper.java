package geo.track.mapper;

import geo.track.domain.Oficinas;
import geo.track.dto.oficinas.response.OficinaResponse;

import java.util.List;
import java.util.stream.Collectors;

public class OficinaMapper {
    public static OficinaResponse toResponse(Oficinas entity) {
        if (entity == null) {
            return null;
        }

        OficinaResponse response = new OficinaResponse();
        response.setIdOficina(entity.getIdOficina());
        response.setRazaoSocial(entity.getRazaoSocial());
        response.setCnpj(entity.getCnpj());
        response.setEmail(entity.getEmail());
        response.setDtCriacao(entity.getDtCriacao());
        response.setStatus(entity.getStatus());

        return response;
    }

    public static List<OficinaResponse> toResponse(List<Oficinas> entities) {
        return entities.stream()
                .map(OficinaMapper::toResponse)
                .collect(Collectors.toList());
    }
}
