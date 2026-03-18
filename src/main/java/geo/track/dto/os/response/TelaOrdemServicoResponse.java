package geo.track.dto.os.response;

import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.dto.veiculos.response.VeiculoResponse;
import geo.track.enums.os.StatusVeiculo;

import java.time.LocalDate;
import java.util.List;

public record TelaOrdemServicoResponse(
        Integer idOrdemServico,
        StatusVeiculo status,
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
