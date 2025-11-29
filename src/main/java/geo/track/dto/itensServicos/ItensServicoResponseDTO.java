package geo.track.dto.itensServicos;

import lombok.Data;

@Data
public class ItensServicoResponseDTO {
    private Integer idItensServicos;
    private Double precoCobrado;
    private String parteVeiculo;
    private String ladoVeiculo;
    private String cor;
    private String especificacaoServico;
    private String observacoesItem;

    private Integer fkServicoId;
    private Integer fkOrdemServicoId;
}
