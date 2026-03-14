package geo.track.dto.os.response;

public record ResumoOrdemServicoResponse(
        Double totalGeral,
        Double totalServico,
        Double totalProdutos,
        Boolean pagamentoRealizado,
        Boolean notaFiscalRealizada,
        Integer produtosSaidaEstoqueConcluida,
        Integer produtosSaidaEstoquePendente
        ) {
}
