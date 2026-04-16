package geo.track.gestao.util;

import geo.track.dto.enderecos.request.RequestPostEndereco;
import geo.track.dto.enderecos.request.RequestPutEndereco;
import geo.track.gestao.entity.Cliente;
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

    public static final Endereco RequestToEndereco(RequestPostEndereco dto, Cliente cliente) {
        if (dto == null) {
            return null; // Or throw an IllegalArgumentException, depending on desired behavior
        }
        return new Endereco(null, dto.getCep(), dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(), dto.getCidade(), dto.getEstado(), dto.getCorrespondencia(), cliente);
    }

    public static Endereco toEntityUpdate(RequestPutEndereco body, Endereco endereco) {
        if (body.getCep() != null) endereco.setCep(body.getCep());
        if (body.getLogradouro() != null) endereco.setLogradouro(body.getLogradouro());
        if (body.getNumero() != null) endereco.setNumero(body.getNumero());
        if (body.getComplemento() != null) endereco.setComplemento(body.getComplemento());
        if (body.getBairro() != null) endereco.setBairro(body.getBairro());
        if (body.getCidade() != null) endereco.setCidade(body.getCidade());
        if (body.getEstado() != null) endereco.setEstado(body.getEstado());
        if (body.getCorrespondencia() != null) endereco.setCorrespondencia(body.getCorrespondencia());

        return endereco;
    }
}
