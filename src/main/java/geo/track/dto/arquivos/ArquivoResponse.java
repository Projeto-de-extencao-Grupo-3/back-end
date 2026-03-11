package geo.track.dto.arquivos;

public record ArquivoResponse(
        Integer id,
        String nome,
        String formato,
        String url,
        String dataCriacao,
        String dataAtualizacao
) {
}
