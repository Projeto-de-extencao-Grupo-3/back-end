package geo.track.dto.registroEntrada.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistroEntradaResponse {
    private Integer idRegistroEntrada;
    private LocalDate dataEntradaPrevista;
    private LocalDate dataEntradaEfetiva;
    private String responsavel;
    private String cpf;
    private Integer extintor;
    private Integer macaco;
    private Integer chaveRoda;
    private Integer geladeira;
    private Integer monitor;
    private Integer estepe;
    private Integer somDvd;
    private Integer caixaFerramenta;
    private Integer idVeiculo;
    private Integer idOrdemDeServico;
}
