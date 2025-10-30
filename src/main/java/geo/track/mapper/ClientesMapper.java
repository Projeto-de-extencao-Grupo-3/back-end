package geo.track.mapper;

import geo.track.domain.Clientes;
import geo.track.domain.Enderecos;
import geo.track.domain.Oficinas;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.dto.clientes.response.ResponseGetCliente;

public class ClientesMapper {
    public static ResponseGetCliente toDTO(Clientes entity) {
        return new ResponseGetCliente(
                entity.getIdCliente(),
                entity.getNome(),
                entity.getCpfCnpj(),
                entity.getTelefone(),
                entity.getEmail(),
                entity.getFkOficina().getIdOficina(),
                entity.getFkEndereco(),
                entity.getVeiculos()
                );
    }

    public static Clientes toEntity(RequestPostCliente dto, Oficinas oficina, Enderecos enderecos) {
        return new Clientes(null, dto.getNome(), dto.getCpfCnpj(), dto.getTelefone(), dto.getEmail(), dto.getTipoCliente(), oficina, enderecos);
    }
}
