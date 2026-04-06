package geo.track.dto.veiculos.response;

public record VeiculoHistoricoResponse(
        Integer idVeiculo,
        String placa,
        String modelo,
        Integer anoModelo,
        String marca,
        String prefixo,
        String nomeCliente,
        Integer idCliente,
        String status
)  {
}
