package geo.track.dto.os.response;

import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.dto.veiculos.response.VeiculoResponse;
import geo.track.enums.os.StatusVeiculo;

import java.time.LocalDate;

public record CardOrdemDeServicoResponse(
        Integer idOrdemServico,
        Double valorTotal,
        LocalDate dataSaidaPrevista,
        LocalDate dataSaidaEfetiva,
        LocalDate dataEntradaPrevista,
        LocalDate dataEntradaEfetiva,
        StatusVeiculo status,
        ClienteResponse cliente,
        VeiculoResponse veiculo
) {
}
