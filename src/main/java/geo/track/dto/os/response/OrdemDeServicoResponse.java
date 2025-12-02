package geo.track.dto.os.response;

import geo.track.enums.os.StatusVeiculo;
import jdk.jshell.Snippet;
import lombok.Data;

import java.time.LocalDate;

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
    private Integer idEntrada;
}
