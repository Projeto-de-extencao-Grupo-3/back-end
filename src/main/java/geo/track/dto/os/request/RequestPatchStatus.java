package geo.track.dto.os.request;

import geo.track.enums.os.StatusVeiculo;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RequestPatchStatus {
    private Integer idOrdem;
    private StatusVeiculo status;
    private Integer fkEntrada;
}
