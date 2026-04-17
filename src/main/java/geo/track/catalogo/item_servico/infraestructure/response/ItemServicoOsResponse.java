package geo.track.catalogo.item_servico.infraestructure.response;

import geo.track.catalogo.item_servico.infraestructure.persistence.entity.Servico;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.LadoVeiculo;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.ParteVeiculo;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.TipoPintura;
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
