package geo.track.jornada.response.entrada;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistroEntradaResponse {
    private Integer idRegistroEntrada;
    private LocalDate dataEntradaPrevista;
    private LocalDate dataEntradaEfetiva;
    private String responsavel;
    private String cpf;
    private Integer idVeiculo;
    private Integer fkOrdemServico;

}
