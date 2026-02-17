package geo.track.dto.os.response;

import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.dto.itensProdutos.ItemProdutoOsResponse;
import geo.track.dto.itensServicos.ItemServicoOsResponse;

import geo.track.dto.registroEntrada.response.RegistroEntradaResponse;
import geo.track.dto.veiculos.response.VeiculoResponse;
import geo.track.enums.os.StatusVeiculo;
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
    private StatusVeiculo status;
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
