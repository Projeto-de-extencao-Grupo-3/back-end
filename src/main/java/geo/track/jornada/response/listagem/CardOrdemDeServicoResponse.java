package geo.track.jornada.response.listagem;

import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.dto.veiculos.response.VeiculoResponse;
import geo.track.jornada.enums.Status;

import java.time.LocalDate;

public record CardOrdemDeServicoResponse(
        Integer idOrdemServico,
        Double valorTotal,
        Long diasEspera,
        LocalDate dataSaidaPrevista,
        LocalDate dataSaidaEfetiva,
        LocalDate dataEntradaPrevista,
        LocalDate dataEntradaEfetiva,
        Status status,
        ClienteResponse cliente,
        VeiculoResponse veiculo
) {
}
