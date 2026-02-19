package geo.track.dto.painelControle.response;

import geo.track.dto.os.response.CardOrdemDeServicoResponse;
import geo.track.dto.os.response.OrdemDeServicoResponse;

import java.util.List;

public record ResponsePainelControle(
        Integer quantidadeOrdens,
        List<CardOrdemDeServicoResponse> ordensDeServico
) {
}
