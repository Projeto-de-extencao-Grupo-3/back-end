package geo.track.gestao.veiculo.infraestructure.response;

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
