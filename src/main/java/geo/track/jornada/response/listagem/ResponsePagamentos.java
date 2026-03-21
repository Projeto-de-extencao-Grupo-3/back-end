package geo.track.jornada.response.listagem;

// so exibir os campos que nao forem null ou vazio pelo jackson

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponsePagamentos(
    Double totalValor,
    Long quantidadeServicosPagamentoPendentes,
    Long quantidadeServicosPagamentoConcluido,
    List<CardOrdemDeServicoResponse> ordensDeServicos
) {
}
