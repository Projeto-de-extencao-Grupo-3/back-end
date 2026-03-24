package geo.track.jornada.response.listagem;

import com.fasterxml.jackson.annotation.JsonInclude;
import geo.track.jornada.enums.Status;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ListagemJornadaResponse {
    private List<OrdemDeServicoResponse> listagemSimples;
    private HashMap<String, Object> listagemAnaliseFinanceira;
    private HashMap<Status, ResponsePainelControle> listagemPainelControle;
    private TelaOrdemServicoResponse buscaSimples;
}
