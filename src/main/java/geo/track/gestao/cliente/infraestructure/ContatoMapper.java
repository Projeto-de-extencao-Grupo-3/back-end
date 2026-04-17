package geo.track.gestao.cliente.infraestructure;

import geo.track.gestao.cliente.infraestructure.request.contato.RequestPostContato;
import geo.track.gestao.cliente.infraestructure.request.contato.RequestPutContato;
import geo.track.gestao.cliente.infraestructure.response.contato.ContatoResponse;
import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Contato;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ContatoMapper {

    public static ContatoResponse toResponse(Contato entity) {
        if (entity == null) {
            return null;
        }

        ContatoResponse response = new ContatoResponse();
        response.setIdContato(entity.getIdContato());
        response.setTelefone(entity.getTelefone());
        response.setEmail(entity.getEmail());
        response.setNomeContato(entity.getNomeContato());
        response.setDepartamentoContato(entity.getDepartamento());
        return response;
    }

    public static List<ContatoResponse> toResponse(List<Contato> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }

        return entities.stream().map(ContatoMapper::toResponse).collect(Collectors.toList());
    }

    public static Contato toEntity(RequestPostContato request, Cliente cliente) {
        if (request == null) {
            return null;
        }

        Contato contato = new Contato();
        contato.setTelefone(request.getTelefone());
        contato.setEmail(request.getEmail());
        contato.setNomeContato(request.getNomeContato());
        contato.setDepartamento(request.getDepartamentoContato());
        contato.setFkCliente(cliente);
        return contato;
    }

    public static Contato toEntityUpdate(Contato contato, RequestPutContato request) {
        if (contato == null || request == null) {
            return contato;
        }

        contato.setTelefone(request.getTelefone());
        contato.setEmail(request.getEmail());
        contato.setNomeContato(request.getNomeContato());
        contato.setDepartamento(request.getDepartamentoContato());
        return contato;
    }
}

