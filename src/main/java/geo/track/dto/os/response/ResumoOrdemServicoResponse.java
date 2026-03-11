package geo.track.dto.os.response;

public record ResumoOrdemServicoResponse(
        Double totalGeral,
        Double totalServico,
        Double totalProdutos,
        Integer produtosSaidaEstoqueRealizada,
        Integer produtosSaidaEstoquePendente,
        Boolean pagamentoRealizado,
        Boolean notaFiscalRealizada
) {
}
