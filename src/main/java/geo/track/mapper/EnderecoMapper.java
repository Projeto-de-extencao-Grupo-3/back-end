package geo.track.mapper;

import geo.track.domain.Enderecos;
import geo.track.dto.enderecos.response.EnderecoResponse;

import java.util.List;
import java.util.stream.Collectors;

public class EnderecoMapper {
    public static EnderecoResponse toResponse(Enderecos entity) {
        if (entity == null) {
            return null;
        }

        EnderecoResponse response = new EnderecoResponse();
        response.setIdEndereco(entity.getIdEndereco());
        response.setCep(entity.getCep());
        response.setLogradouro(entity.getLogradouro());
        response.setNumero(entity.getNumero());
        response.setComplemento(entity.getComplemento());
        response.setBairro(entity.getBairro());
        response.setCidade(entity.getCidade());
        response.setEstado(entity.getEstado());

        return response;
    }

    public static List<EnderecoResponse> toResponse(List<Enderecos> entities) {
        return entities.stream()
                .map(EnderecoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
