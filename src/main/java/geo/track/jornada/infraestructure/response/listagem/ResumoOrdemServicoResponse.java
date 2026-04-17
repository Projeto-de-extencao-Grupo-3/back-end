package geo.track.jornada.infraestructure.response.listagem;

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
