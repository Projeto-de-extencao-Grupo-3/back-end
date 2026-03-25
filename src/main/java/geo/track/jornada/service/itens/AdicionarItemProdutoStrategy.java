package geo.track.jornada.service.itens;

import geo.track.gestao.entity.ItemProduto;
import geo.track.gestao.service.produto.AdicionarItemProdutoUseCase;
import geo.track.jornada.request.itens.RequestPostItemProduto;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ADICIONAR SERVICO AO ORÇAMENTO **/
@Component
@RequiredArgsConstructor
public class AdicionarItemProdutoStrategy implements ItensJornadaStrategy {
    private final AdicionarItemProdutoUseCase ADICIONAR_ITEM_PRODUTO_USE_CASE;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.ADICIONAR_ITEM_PRODUTO.equals(tipoJornada);
    }

    @Override
    public ItemProduto execute(Integer idOrdemServico, GetJornada getRequest) {
        RequestPostItemProduto request = (RequestPostItemProduto) getRequest;
        return ADICIONAR_ITEM_PRODUTO_USE_CASE.execute(request);
    }
}
