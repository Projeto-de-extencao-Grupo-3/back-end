package geo.track.externo.arquivo.infraestructure;

import geo.track.externo.arquivo.infraestructure.persistence.entity.Arquivo;
import geo.track.externo.arquivo.infraestructure.response.ArquivoResponse;

public class ArquivoMapper {
    public static ArquivoResponse toResponse(Arquivo entity) {
        return new ArquivoResponse(entity.getIdArquivo(), entity.getNome(), entity.getFormato().toString(), entity.getUrl(), entity.getDataCriacao().toString(), entity.getDataAtualizacao().toString(), entity.getStatus().name());
    }
}
