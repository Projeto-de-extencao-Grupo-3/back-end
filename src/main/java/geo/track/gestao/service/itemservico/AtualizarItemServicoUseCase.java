package geo.track.gestao.service.itemservico;

import geo.track.gestao.entity.ItemServico;
import geo.track.dto.itensServicos.RequestPutItemServico;

public interface AtualizarItemServicoUseCase {
    ItemServico execute(Integer id, RequestPutItemServico request);
}

