package geo.track.jornada.request.itens;

import geo.track.gestao.enums.Servico;
import geo.track.gestao.enums.LadoVeiculo;
import geo.track.gestao.enums.ParteVeiculo;
import geo.track.gestao.enums.TipoPintura;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestPostItemServico implements GetJornada {
    @NotNull
    private Double precoCobrado;
    @NotNull
    private ParteVeiculo parteVeiculo;
    @NotNull
    private LadoVeiculo ladoVeiculo;

    @NotNull
    private Servico tipoServico;

    private String cor;
    private String especificacaoServico;

    @Enumerated(EnumType.STRING)
    private TipoPintura tipoPintura;

    @Override
    public TipoJornada getTipoJornada() {
        return TipoJornada.ADICIONAR_ITEM_SERVICO;
    }
}
