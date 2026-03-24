package geo.track.jornada.response.listagem;

import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.dto.veiculos.response.VeiculoResponse;
import geo.track.jornada.enums.Status;

import java.time.LocalDate;
import java.util.List;

public record TelaOrdemServicoResponse(
        Integer idOrdemServico,
        Status status,
        LocalDate dataEntradaPrevista,
        LocalDate dataEntradaEfetiva,
        LocalDate dataSaidaPrevista,
        LocalDate dataSaidaEfetiva,
        ClienteResponse cliente,
        VeiculoResponse veiculo,
        ResumoOrdemServicoResponse resumo,
        List<ItemServicoResponse> servicos,
        List<ItemProdutoResponse> produtos
) {
}
