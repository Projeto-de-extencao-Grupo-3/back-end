package geo.track.mapper;

import geo.track.domain.Oficina;
import geo.track.dto.oficinas.request.RequestPostOficina;
import geo.track.dto.oficinas.request.RequestPutOficina;
import geo.track.dto.oficinas.response.OficinaResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OficinaMapper {
    public static OficinaResponse toResponse(Oficina entity) {
        if (entity == null) {
            return null;
        }

        OficinaResponse response = new OficinaResponse();
        response.setIdOficina(entity.getIdOficina());
        response.setRazaoSocial(entity.getRazaoSocial());
        response.setCnpj(entity.getCnpj());
        response.setEmail(entity.getEmail());
        response.setDtCriacao(entity.getDataCriacao());
        response.setStatus(entity.getStatus());

        return response;
    }

    public static List<OficinaResponse> toResponse(List<Oficina> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(OficinaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static Oficina toEntity(RequestPostOficina dto) {
        if (dto == null) {
            return null;
        }
        Oficina oficina = new Oficina();
        oficina.setRazaoSocial(dto.getRazaoSocial());
        oficina.setCnpj(dto.getCnpj());
        oficina.setEmail(dto.getEmail());
        oficina.setStatus(dto.getStatus() != null ? dto.getStatus() : true);
        return oficina;
    }

    public static Oficina toEntityUpdate(Oficina existente, RequestPutOficina dto) {
        if (dto == null) {
            return existente;
        }
        if (dto.getEmail() != null) existente.setEmail(dto.getEmail());
        if (dto.getStatus() != null) existente.setStatus(dto.getStatus());
        return existente;
    }
}
