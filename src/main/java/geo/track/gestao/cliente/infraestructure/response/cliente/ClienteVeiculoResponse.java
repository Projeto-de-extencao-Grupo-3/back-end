package geo.track.gestao.cliente.infraestructure.response.cliente;

import geo.track.gestao.veiculo.infraestructure.response.VeiculoResponse;

public record ClienteVeiculoResponse(
        Integer idCliente,
        String nome,
        String cpfCnpj,
        String tipoCliente,
        VeiculoResponse veiculo
) {
}
