package geo.track.gestao.util;

import geo.track.dto.enderecos.request.RequestPostEndereco;
import geo.track.gestao.entity.Endereco;
import geo.track.dto.enderecos.response.EnderecoResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EnderecoMapper {
    public static EnderecoResponse toResponse(Endereco entity) {
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

    public static List<EnderecoResponse> toResponse(List<Endereco> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(EnderecoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static final Endereco RequestToEndereco(RequestPostEndereco dto) {
        if (dto == null) {
            return null; // Or throw an IllegalArgumentException, depending on desired behavior
        }
        return new Endereco(null, dto.getCep(), dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(), dto.getCidade(), dto.getEstado(),null);
    }
}
