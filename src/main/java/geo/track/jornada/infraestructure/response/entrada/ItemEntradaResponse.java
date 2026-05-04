package geo.track.jornada.infraestructure.response.entrada;

public record ItemEntradaResponse(
        Integer idItemEntrada,
        String nomeItem,
        Integer quantidade
) {
}
