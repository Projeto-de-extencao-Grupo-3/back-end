package geo.track.external.util;

import geo.track.external.entity.Arquivo;
import geo.track.external.request.arquivo.ArquivoResponse;

public class ArquivoMapper {
    public static ArquivoResponse toResponse(Arquivo entity) {
        return new ArquivoResponse(entity.getIdArquivo(), entity.getNome(), entity.getFormato().toString(), entity.getUrl(), entity.getDataCriacao().toString(), entity.getDataAtualizacao().toString(), entity.getStatus().name());
    }
}
