package geo.track.jornada.infraestructure.response.listagem;

import geo.track.gestao.cliente.infraestructure.response.cliente.ClienteResponse;
import geo.track.catalogo.item_produto.infraestructure.response.ItemProdutoResponse;
import geo.track.catalogo.item_servico.infraestructure.response.ItemServicoResponse;
import geo.track.gestao.veiculo.infraestructure.response.VeiculoResponse;
import geo.track.jornada.infraestructure.persistence.entity.Status;

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
