package geo.track.dto.itensServicos;

import geo.track.gestao.enums.Servico;
import geo.track.gestao.enums.LadoVeiculo;
import geo.track.gestao.enums.ParteVeiculo;
import geo.track.gestao.enums.TipoPintura;
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
    @Enumerated(EnumType.STRING)
    private TipoPintura tipoPintura;

    // Campos de Servico
    @Enumerated(EnumType.STRING)
    private Servico tipoServico;
}
