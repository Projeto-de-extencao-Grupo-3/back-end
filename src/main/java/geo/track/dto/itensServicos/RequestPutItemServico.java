package geo.track.dto.itensServicos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import geo.track.gestao.enums.Servico;
import geo.track.gestao.enums.LadoVeiculo;
import geo.track.gestao.enums.ParteVeiculo;
import geo.track.gestao.enums.TipoPintura;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

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
