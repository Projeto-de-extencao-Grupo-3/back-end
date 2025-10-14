package geo.track.dto.registroEntrada.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class RequestPutRegistroEntrada {
    private Integer idRegistro;
    private LocalDate dtEntradaEfetiva;
    private String responsavel;
    private String cpf;
    private Boolean extintor;
    private Boolean macaco;
    private Boolean chaveRoda;
    private Integer geladeira;
    private Integer monitor;
    private Integer fkVeiculo;
}
