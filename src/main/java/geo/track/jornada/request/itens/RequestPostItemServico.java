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

public record RequestPostItemServico(
    @NotNull
    Double precoCobrado,
    @NotNull
    ParteVeiculo parteVeiculo,
    @NotNull
    LadoVeiculo ladoVeiculo,

    @NotNull
    Servico tipoServico,

    String cor,
    String especificacaoServico,

    @Enumerated(EnumType.STRING)
    TipoPintura tipoPintura
){}
