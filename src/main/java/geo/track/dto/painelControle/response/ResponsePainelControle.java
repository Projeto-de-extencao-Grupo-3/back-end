package geo.track.dto.painelControle.response;

import geo.track.dto.os.response.CardOrdemDeServicoResponse;

import java.util.List;

public record ResponsePainelControle(
        Integer quantidadeOrdens,
        List<CardOrdemDeServicoResponse> ordensDeServico
) {
}
