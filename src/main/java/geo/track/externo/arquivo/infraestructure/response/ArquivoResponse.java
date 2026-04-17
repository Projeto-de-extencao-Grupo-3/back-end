package geo.track.externo.arquivo.infraestructure.response;

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
