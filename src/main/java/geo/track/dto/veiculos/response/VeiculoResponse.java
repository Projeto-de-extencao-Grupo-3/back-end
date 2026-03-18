package geo.track.dto.veiculos.response;

import geo.track.domain.Cliente;
import lombok.Data;

@Data
public class VeiculoResponse {
    private Integer idVeiculo;
    private String placa;
    private String modelo;
    private Integer anoModelo;
    private String marca;
    private String prefixo;
    private String nomeCliente;
    private Integer idCliente;
}
