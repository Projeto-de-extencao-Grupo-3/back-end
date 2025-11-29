package geo.track.dto.itensServicos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItensServicoRequestDTO {

    @NotNull
    private Double precoCobrado;

    @NotBlank
    private String parteVeiculo;

    @NotBlank
    private String ladoVeiculo;

    @NotBlank
    private String cor;

    @NotBlank
    private String especificacaoServico;

    private String observacoesItem;

    private Integer fkServicoId;

    private Integer fkOrdemServicoId;
}