package geo.track.dto.analise.financeira.response;

// so exibir os campos que nao forem null ou vazio pelo jackson

import com.fasterxml.jackson.annotation.JsonInclude;
import geo.track.dto.os.response.CardOrdemDeServicoResponse;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponsePagamentos(
    Double totalValor,
    Long quantidadeServicosPagamentoPendentes,
    Long quantidadeServicosPagamentoConcluido,
    List<CardOrdemDeServicoResponse> ordensDeServicos
) {
}
