package geo.track.gestao.veiculo.infraestructure.response;

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
