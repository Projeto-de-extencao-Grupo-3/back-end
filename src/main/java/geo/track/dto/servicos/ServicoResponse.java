package geo.track.dto.servicos;

import geo.track.enums.Servico;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ServicoResponse {
    private Integer idServico;
    @Enumerated(EnumType.STRING)
    private Servico tipoServico;
    private String tituloServico;
    private Integer tempoBase;
    private boolean ativo;
}
