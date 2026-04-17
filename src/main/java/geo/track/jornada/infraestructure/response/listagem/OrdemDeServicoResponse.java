package geo.track.jornada.infraestructure.response.listagem;

import geo.track.gestao.cliente.infraestructure.response.cliente.ClienteResponse;
import geo.track.catalogo.item_produto.infraestructure.response.ItemProdutoOsResponse;
import geo.track.catalogo.item_servico.infraestructure.response.ItemServicoOsResponse;

import geo.track.gestao.veiculo.infraestructure.response.VeiculoResponse;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.infraestructure.response.entrada.RegistroEntradaResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrdemDeServicoResponse {
    private Integer idOrdemServico;
    private Double valorTotal;
    private Double valorTotalServicos;
    private Double valorTotalProdutos;
    private LocalDate dataSaidaPrevista;
    private LocalDate dataSaidaEfetiva;
    private Status status;
    private Boolean seguradora;
    private Boolean nfRealizada;
    private Boolean pagtRealizado;
    private Boolean ativo;
    private ClienteResponse cliente;
    private VeiculoResponse veiculo;
    private RegistroEntradaResponse entrada;
    private List<ItemServicoOsResponse> servicos;
    private List<ItemProdutoOsResponse> produtos;
}
