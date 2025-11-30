package geo.track.dto.veiculos.response;

import lombok.Data;

@Data
public class VeiculoResponse {
    private Integer idVeiculo;
    private String placa;
    private String marca;
    private String modelo;
    private Integer anoModelo;
    private Integer idCliente;
}
