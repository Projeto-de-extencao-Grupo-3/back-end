package geo.track.mapper;

import geo.track.entity.Arquivo;
import geo.track.dto.arquivos.ArquivoResponse;

public class ArquivoMapper {
    public static ArquivoResponse toResponse(Arquivo entity) {
        return new ArquivoResponse(entity.getIdArquivo(), entity.getNome(), entity.getFormato().toString(), entity.getUrl(), entity.getDataCriacao().toString(), entity.getDataAtualizacao().toString(), entity.getStatus().name());
    }
}
