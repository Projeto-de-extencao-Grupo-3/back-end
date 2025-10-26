package geo.track.mapper;

import geo.track.domain.Clientes;
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
}
