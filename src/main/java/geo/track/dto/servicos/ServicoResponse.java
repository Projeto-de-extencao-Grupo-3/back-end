package geo.track.dto.servicos;

import lombok.Data;

@Data
public class ServicoResponse {
    private Integer idServico;
    private String tipoServico;
    private String tituloServico;
    private Integer tempoBase;
    private boolean ativo;
}
