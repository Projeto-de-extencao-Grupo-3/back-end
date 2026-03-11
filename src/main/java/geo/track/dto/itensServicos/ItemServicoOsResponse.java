package geo.track.dto.itensServicos;

import geo.track.enums.Servico;
import geo.track.enums.servico.LadoVeiculo;
import geo.track.enums.servico.ParteVeiculo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ItemServicoOsResponse {
    private Integer idRegistroServico;
    private Double precoCobrado;
    @Enumerated(EnumType.STRING)
    private ParteVeiculo parteVeiculo;
    @Enumerated(EnumType.STRING)
    private LadoVeiculo ladoVeiculo;
    private String cor;
    private String especificacaoServico;
    private String observacoesItem;

    // Campos de Servico
    private String nomeServico;
    @Enumerated(EnumType.STRING)
    private Servico tipoServico;
    private Integer tempoBase;
}
