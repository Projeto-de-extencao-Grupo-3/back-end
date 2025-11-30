package geo.track.dto.itensservicos;

import lombok.Data;

@Data
public class ItensServicoResponse {
    private Integer idItensServicos;
    private Double precoCobrado;
    private String parteVeiculo;
    private String ladoVeiculo;
    private String cor;
    private String especificacaoServico;
    private String observacoesItem;
    private Integer idServico;
    private Integer idOrdemServico;
}
