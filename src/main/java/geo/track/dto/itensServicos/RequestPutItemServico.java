package geo.track.dto.itensServicos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import geo.track.gestao.enums.Servico;
import geo.track.gestao.enums.LadoVeiculo;
import geo.track.gestao.enums.ParteVeiculo;
import geo.track.gestao.enums.TipoPintura;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class RequestPutItemServico {
    private Double precoCobrado;
    private ParteVeiculo parteVeiculo;
    private LadoVeiculo ladoVeiculo;
    private String cor;
    private String especificacaoServico;
    @Enumerated(EnumType.STRING)
    private TipoPintura tipoPintura;
    private Servico tipoServico;
}
