package geo.track.dto.itensServicos;

import geo.track.enums.servico.LadosVeiculo;
import geo.track.enums.servico.PartesVeiculo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ItemServicoResponse {
    private Integer idItensServicos;
    private Double precoCobrado;
    @Enumerated(EnumType.STRING)
    private PartesVeiculo parteVeiculo;
    @Enumerated(EnumType.STRING)
    private LadosVeiculo ladoVeiculo;
    private String cor;
    private String especificacaoServico;
    private String observacoesItem;
    private Integer idServico;
    private Integer idOrdemServico;
}
