package geo.track.dto.veiculos.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPutVeiculos {
    private Integer idVeiculo;
    private String placa;
    private String marca;
    private Integer anoModelo;
    private Integer anoFabricacao;
    private String cor;

}
