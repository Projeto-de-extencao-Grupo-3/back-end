package geo.track.mapper;

import geo.track.entity.Cliente;
import geo.track.entity.Veiculo;
import geo.track.dto.veiculos.request.RequestPostVeiculo;
import geo.track.dto.veiculos.request.RequestPutVeiculo;
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
        response.setMarca(entity.getMarca());
        response.setPrefixo(entity.getPrefixo());
        response.setIdCliente(entity.getFkCliente().getIdCliente());
        response.setNomeCliente(entity.getFkCliente().getNome());
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

    public static Veiculo toEntity(RequestPostVeiculo dto) {
        if (dto == null) {
            return null;
        }
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setMarca(dto.getMarca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setPrefixo(dto.getPrefixo());
        veiculo.setAnoModelo(dto.getAnoModelo());

        Cliente cliente = new Cliente();
        cliente.setIdCliente(dto.getIdCliente());

        veiculo.setFkCliente(cliente);

        return veiculo;
    }

    public static Veiculo toEntityUpdate(Veiculo existente, RequestPutVeiculo dto) {
        if (dto == null) {
            return existente;
        }
        if (dto.getMarca() != null) existente.setMarca(dto.getMarca());
        if (dto.getModelo() != null) existente.setModelo(dto.getModelo());
        if (dto.getPrefixo() != null) existente.setPrefixo(dto.getPrefixo());
        if (dto.getAnoModelo() != null) existente.setAnoModelo(dto.getAnoModelo());
        return existente;
    }
}
