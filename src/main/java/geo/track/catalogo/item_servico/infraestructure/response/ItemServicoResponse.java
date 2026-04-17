package geo.track.catalogo.item_servico.infraestructure.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.Servico;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.LadoVeiculo;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.ParteVeiculo;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.TipoPintura;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemServicoResponse {
    private Integer idItemServico;
    @Enumerated(EnumType.STRING)
    private Servico tipoServico;
    @Enumerated(EnumType.STRING)
    private ParteVeiculo parteVeiculo;
    @Enumerated(EnumType.STRING)
    private LadoVeiculo ladoVeiculo;
    private Double precoCobrado;
    private String cor;
    private String especificacaoServico;
    private TipoPintura tipoPintura;
}
