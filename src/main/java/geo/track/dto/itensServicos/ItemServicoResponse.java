package geo.track.dto.itensServicos;

import com.fasterxml.jackson.annotation.JsonInclude;
import geo.track.gestao.enums.Servico;
import geo.track.gestao.enums.LadoVeiculo;
import geo.track.gestao.enums.ParteVeiculo;
import geo.track.gestao.enums.TipoPintura;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemServicoResponse {
    private Integer idServico;
    @Enumerated(EnumType.STRING)
    private Servico tipoServico;
    @Enumerated(EnumType.STRING)
    private ParteVeiculo parteVeiculo;
    @Enumerated(EnumType.STRING)
    private LadoVeiculo ladoVeiculo;
    private Double precoCobrado;
    private String cor;
    private String especificacaoServico;
    private TipoPintura tipoPintura;
}
