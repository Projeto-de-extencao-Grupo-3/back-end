package geo.track.mapper;

import geo.track.domain.Clientes;
import geo.track.domain.Enderecos;
import geo.track.domain.Oficinas;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.dto.clientes.response.ClienteResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ClientesMapper {
    public static ClienteResponse toResponse(Clientes entity) {
        if (entity == null) {
            return null;
        }

        ClienteResponse response = new ClienteResponse();
        response.setIdCliente(entity.getIdCliente());
        response.setNome(entity.getNome());
        response.setCpfCnpj(entity.getCpfCnpj());
        response.setTelefone(entity.getTelefone());
        response.setEmail(entity.getEmail());
        response.setTipoCliente(entity.getTipoCliente());

        if (entity.getFkOficina() != null) {
            response.setIdOficina(entity.getFkOficina().getIdOficina());
        }

        if (entity.getFkEndereco() != null) {
            response.setIdEndereco(entity.getFkEndereco().getIdEndereco());
        }

        return response;
    }

    public static List<ClienteResponse> toResponse(List<Clientes> entities) {
        return entities.stream()
                .map(ClientesMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static Clientes toEntity(RequestPostCliente dto, Oficinas oficina, Enderecos enderecos) {
        return new Clientes(null, dto.getNome(), dto.getCpfCnpj(), dto.getTelefone(), dto.getEmail(), dto.getTipoCliente() , oficina, enderecos);
    }
}
