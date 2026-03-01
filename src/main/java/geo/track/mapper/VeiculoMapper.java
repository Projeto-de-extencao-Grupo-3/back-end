package geo.track.mapper;

import geo.track.domain.Veiculo;
import geo.track.dto.veiculos.response.VeiculoResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VeiculoMapper {
    public static VeiculoResponse toResponse(Veiculo entity) {
        if (entity == null) {
            return null;
        }

        VeiculoResponse response = new VeiculoResponse();
        response.setIdVeiculo(entity.getIdVeiculo());
        response.setPlaca(entity.getPlaca());
        response.setModelo(entity.getModelo());
        response.setAnoModelo(entity.getAnoModelo());
        // Assuming fkCliente is not directly mapped to response, or handled elsewhere if needed.

        return response;
    }

    public static List<VeiculoResponse> toResponse(List<Veiculo> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(VeiculoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
