package geo.track.dto.analise.financeira.response;

// so exibir os campos que nao forem null ou vazio pelo jackson

import com.fasterxml.jackson.annotation.JsonInclude;
import geo.track.dto.os.response.OrdemDeServicoResponse;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponsePagamentos(
    Double totalValorPendente,
    Long quantidadeServicosPagamentoPendentes,
    Long quantidadeServicosPagamentoConcluido,
    List<OrdemDeServicoResponse> ordensDeServicos
) {
}
