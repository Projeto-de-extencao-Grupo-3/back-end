package geo.track.jornada.service.itens;

import geo.track.dto.itensProdutos.RequestPutItemProduto;
import geo.track.gestao.entity.ItemProduto;
import geo.track.gestao.service.produto.AtualizarItemProdutoUseCase;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ADICIONAR SERVICO AO ORÇAMENTO **/
@Component
@RequiredArgsConstructor
public class AtualizarItemProdutoStrategy implements ItensJornadaStrategy {
    private final AtualizarItemProdutoUseCase ATUALIZAR_ITEM_PRODUTO_USE_CASE;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.ATUALIZAR_ITEM_PRODUTO.equals(tipoJornada);
    }

    @Override
    public ItemProduto execute(Integer idItemProduto, GetJornada getRequest) {
        RequestPutItemProduto request = (RequestPutItemProduto) getRequest;
        return ATUALIZAR_ITEM_PRODUTO_USE_CASE.execute(idItemProduto, request);
    }
}
