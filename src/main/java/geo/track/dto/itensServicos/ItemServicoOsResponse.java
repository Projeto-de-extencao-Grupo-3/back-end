package geo.track.dto.itensServicos;

import geo.track.enums.servico.LadosVeiculo;
import geo.track.enums.servico.PartesVeiculo;
import geo.track.enums.servico.TipoServico;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ItemServicoOsResponse {
    private Integer idRegistroServico;
    private Double precoCobrado;
    @Enumerated(EnumType.STRING)
    private PartesVeiculo parteVeiculo;
    @Enumerated(EnumType.STRING)
    private LadosVeiculo ladoVeiculo;
    private String cor;
    private String especificacaoServico;
    private String observacoesItem;

    // Campos de Servico
    private String nomeServico;
    @Enumerated(EnumType.STRING)
    private TipoServico tipoServico;
    private Integer tempoBase;
}
