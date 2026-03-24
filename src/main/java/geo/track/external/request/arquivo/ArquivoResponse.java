package geo.track.external.request.arquivo;

public record ArquivoResponse(
        Integer id,
        String nome,
        String formato,
        String url,
        String dataCriacao,
        String dataAtualizacao,
        String status
) {
}
