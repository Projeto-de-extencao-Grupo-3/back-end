package geo.track.catalogo.item_servico.application;

import geo.track.catalogo.item_servico.infraestructure.persistence.entity.ItemServico;
import geo.track.catalogo.item_servico.infraestructure.request.RequestPostItemServico;

public interface AdicionarItemServicoUseCase {
    ItemServico execute(Integer idOficina, RequestPostItemServico request);
}

