package geo.track.catalogo.item_servico.infraestructure.request;

import geo.track.catalogo.item_servico.infraestructure.persistence.entity.Servico;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.LadoVeiculo;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.ParteVeiculo;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.TipoPintura;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

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
