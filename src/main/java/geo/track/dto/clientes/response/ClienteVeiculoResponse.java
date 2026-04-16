package geo.track.dto.clientes.response;

import geo.track.dto.veiculos.response.VeiculoResponse;

public record ClienteVeiculoResponse(
        Integer idCliente,
        String nome,
        String cpfCnpj,
        String tipoCliente,
        VeiculoResponse veiculo
) {
}
