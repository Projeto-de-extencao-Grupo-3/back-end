package geo.track.externo.arquivo.infraestructure.response;

public record ArquivoResponse(
        Integer id,
        String nome,
        String categoria,
        String formato,
        String url,
        String dataCriacao,
        String dataAtualizacao
) {
}
