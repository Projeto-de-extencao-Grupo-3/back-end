package geo.track.jornada.infraestructure.response.listagem;

import java.util.List;

public record ResponsePainelControle(
        Integer quantidadeOrdens,
        List<CardOrdemDeServicoResponse> ordensDeServico
) {
}
