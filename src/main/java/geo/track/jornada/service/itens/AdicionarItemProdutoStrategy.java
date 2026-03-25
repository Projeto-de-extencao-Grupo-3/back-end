package geo.track.jornada.service.itens;

import geo.track.gestao.entity.ItemProduto;
import geo.track.gestao.entity.Produto;
import geo.track.gestao.service.produto.AdicionarItemProdutoUseCase;
import geo.track.jornada.request.itens.RequestPostItemProduto;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.gestao.util.ItemProdutoMapper;
import geo.track.service.OrdemDeServicoService;
import geo.track.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ADICIONAR SERVICO AO ORÇAMENTO **/
@Component
@RequiredArgsConstructor
public class AdicionarItemProdutoStrategy implements ItensJornadaStrategy {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final AdicionarItemProdutoUseCase ADICIONAR_ITEM_PRODUTO_USE_CASE;
    private final ProdutoService PRODUTO_SERVICE;

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
