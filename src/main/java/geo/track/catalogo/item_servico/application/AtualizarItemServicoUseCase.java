package geo.track.catalogo.item_servico.application;

import geo.track.catalogo.item_servico.infraestructure.persistence.entity.ItemServico;
import geo.track.catalogo.item_servico.infraestructure.request.RequestPutItemServico;

public interface AtualizarItemServicoUseCase {
    ItemServico execute(Integer id, RequestPutItemServico request);
}

