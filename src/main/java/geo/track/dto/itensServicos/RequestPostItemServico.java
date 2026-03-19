package geo.track.dto.itensServicos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import geo.track.enums.Servico;
import geo.track.enums.servico.LadoVeiculo;
import geo.track.enums.servico.ParteVeiculo;
import geo.track.enums.servico.TipoPintura;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class RequestPostItemServico {
    @NotNull
    private Double precoCobrado;
    @NotNull
    private ParteVeiculo parteVeiculo;
    @NotNull
    private LadoVeiculo ladoVeiculo;
    @NotNull
    private Servico tipoServico;
    @NotNull
    private Integer fkOrdemServico;

    private String cor;
    private String especificacaoServico;
    @Enumerated(EnumType.STRING)
    private TipoPintura tipoPintura;
}
