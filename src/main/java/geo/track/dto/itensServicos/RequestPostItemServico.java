package geo.track.dto.itensServicos;

import geo.track.enums.servico.LadosVeiculo;
import geo.track.enums.servico.PartesVeiculo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestPostItemServico {
    @NotNull
    private Double precoCobrado;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PartesVeiculo parteVeiculo;
    @NotNull
    @Enumerated(EnumType.STRING)
    private LadosVeiculo ladoVeiculo;
    @NotBlank
    private String cor;
    @NotBlank
    private String especificacaoServico;
    @NotBlank
    private String observacoesItem;
    @NotNull
    private Integer fkServico;
    @NotNull
    private Integer fkOrdemServico;
}
