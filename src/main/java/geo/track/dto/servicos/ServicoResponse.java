package geo.track.dto.servicos;

import geo.track.enums.servico.TipoServico;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ServicoResponse {
    private Integer idServico;
    @Enumerated(EnumType.STRING)
    private TipoServico tipoServico;
    private String tituloServico;
    private Integer tempoBase;
    private boolean ativo;
}
