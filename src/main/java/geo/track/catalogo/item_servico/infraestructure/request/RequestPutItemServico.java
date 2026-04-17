package geo.track.catalogo.item_servico.infraestructure.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.Servico;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.LadoVeiculo;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.ParteVeiculo;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.TipoPintura;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record RequestPutItemServico(
        Double precoCobrado,
        ParteVeiculo parteVeiculo,
        LadoVeiculo ladoVeiculo,
        String cor,
        String especificacaoServico,
        @Enumerated(EnumType.STRING)
        TipoPintura tipoPintura,
        Servico tipoServico
) {
}
