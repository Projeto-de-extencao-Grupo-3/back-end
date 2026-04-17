package geo.track.jornada.infraestructure.response.listagem;

import geo.track.gestao.cliente.infraestructure.response.cliente.ClienteResponse;
import geo.track.gestao.veiculo.infraestructure.response.VeiculoResponse;
import geo.track.jornada.infraestructure.persistence.entity.Status;

import java.time.LocalDate;

public record CardOrdemDeServicoResponse(
        Integer idOrdemServico,
        Integer fkEntrada,
        Double valorTotal,
        Long diasEspera,
        LocalDate dataUltimaAtualizacao,
        LocalDate dataSaidaPrevista,
        LocalDate dataSaidaEfetiva,
        LocalDate dataEntradaPrevista,
        LocalDate dataEntradaEfetiva,
        Status status,
        ClienteResponse cliente,
        VeiculoResponse veiculo
) {
}
