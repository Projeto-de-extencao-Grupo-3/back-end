package geo.track.jornada.request;


import geo.track.jornada.enums.MapListagem;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.Month;
import java.time.Year;

public record ListagemJornadaParams(
        @Pattern(regexp = "\\d{4}-\\d{2}", message = "O parametro deve seguir o formato de 'ano-mes'")
        String anoMes,

        MapListagem map
) {
    public Boolean isListagemAnaliseFinanceira() {
        return anoMes != null && MapListagem.ANALISE_FINANCEIRA.equals(map);
    }

    public Boolean isListagemPainelControle() {
        return anoMes == null && MapListagem.PAINEL_CONTROLE.equals(map);
    }

    public Boolean isListagemSimples() {
        return  anoMes == null && map == null;
    }
}
