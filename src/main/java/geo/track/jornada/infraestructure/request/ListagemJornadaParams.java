package geo.track.jornada.infraestructure.request;


import geo.track.jornada.application.MapListagem;
import jakarta.validation.constraints.Pattern;

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
