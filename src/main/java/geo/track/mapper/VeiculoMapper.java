package geo.track.mapper;

import geo.track.domain.Veiculos;
import geo.track.dto.veiculos.response.VeiculoResponse;

import java.util.List;
import java.util.stream.Collectors;

public class VeiculoMapper {
    public static VeiculoResponse toResponse(Veiculos entity) {
        if (entity == null) {
            return null;
        }

        VeiculoResponse response = new VeiculoResponse();
        response.setIdVeiculo(entity.getIdVeiculo());
        response.setPlaca(entity.getPlaca());
        response.setMarca(entity.getMarca());
        response.setModelo(entity.getModelo());
        response.setAnoModelo(entity.getAnoModelo());

        if (entity.getFkCliente() != null) {
            response.setIdCliente(entity.getFkCliente().getIdCliente());
        }

        return response;
    }

    public static List<VeiculoResponse> toResponse(List<Veiculos> entities) {
        return entities.stream()
                .map(VeiculoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
