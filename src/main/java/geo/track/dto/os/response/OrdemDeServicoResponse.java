package geo.track.dto.os.response;

import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensServicos.ItemServicoResponse;

import geo.track.enums.os.StatusVeiculo;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrdemDeServicoResponse {
    private Integer idOrdemServico;
    private Double valorTotal;
    private LocalDate dtSaidaPrevista;
    private LocalDate dtSaidaEfetiva;
    private StatusVeiculo status;
    private Boolean seguradora;
    private Boolean nfRealizada;
    private Boolean pagtRealizado;
    private Boolean ativo;
    private List<ItemServicoResponse> servicos;
    private List<ItemProdutoResponse> produtos;
    private Integer idEntrada;
}
