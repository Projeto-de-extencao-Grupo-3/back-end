package geo.track.jornada.infraestructure.response.listagem;

public record  ViewPagtoPendente(
        Double totalValorNaoPago,
        Long quantidadeServicosNaoPagos
) {
}
