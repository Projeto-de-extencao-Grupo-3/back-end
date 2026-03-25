package geo.track.jornada.service.itens;

import geo.track.dto.itensProdutos.RequestPutItemProduto;
import geo.track.dto.itensServicos.RequestPutItemServico;
import geo.track.gestao.entity.ItemProduto;
import geo.track.gestao.entity.ItemServico;
import geo.track.gestao.service.itemservico.AtualizarItemServicoUseCase;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ADICIONAR SERVICO AO ORÇAMENTO **/
@Component
@RequiredArgsConstructor
public class AtualizarItemServicoStrategy implements ItensJornadaStrategy {
    private final AtualizarItemServicoUseCase ATUALIZAR_ITEM_SERVICO_USE_CASE;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.ATUALIZAR_ITEM_SERVICO.equals(tipoJornada);
    }

    @Override
    public ItemServico execute(Integer idItemServico, GetJornada getRequest) {
        RequestPutItemServico request = (RequestPutItemServico) getRequest;
        return ATUALIZAR_ITEM_SERVICO_USE_CASE.execute(idItemServico, request);
    }
}
