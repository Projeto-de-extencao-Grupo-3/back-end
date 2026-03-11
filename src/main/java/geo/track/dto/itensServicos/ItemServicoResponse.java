package geo.track.dto.itensServicos;

import com.fasterxml.jackson.annotation.JsonInclude;
import geo.track.enums.servico.LadoVeiculo;
import geo.track.enums.servico.ParteVeiculo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemServicoResponse {
    private Integer idItensServicos;
    private Double precoCobrado;
    @Enumerated(EnumType.STRING)
    private ParteVeiculo parteVeiculo;
    @Enumerated(EnumType.STRING)
    private LadoVeiculo ladoVeiculo;
    private String cor;
    private String especificacaoServico;
    private String observacoesItem;
    private Integer idServico;
    private Integer idOrdemServico;
}
