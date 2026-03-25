package geo.track.gestao.service.itemservico;

import geo.track.gestao.entity.ItemServico;
import geo.track.jornada.request.itens.RequestPostItemServico;

public interface AdicionarItemServicoUseCase {
    ItemServico execute(RequestPostItemServico request, Integer idOficina);
}

