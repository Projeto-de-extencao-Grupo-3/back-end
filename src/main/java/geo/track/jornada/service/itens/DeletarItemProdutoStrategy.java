package geo.track.jornada.service.itens;

import geo.track.dto.itensProdutos.RequestPutItemProduto;
import geo.track.gestao.entity.ItemProduto;
import geo.track.gestao.service.produto.DeletarItemProdutoUseCase;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ADICIONAR SERVICO AO ORÇAMENTO **/
@Component
@RequiredArgsConstructor
public class DeletarItemProdutoStrategy implements ItensJornadaStrategy {
    private final DeletarItemProdutoUseCase DELETAR_ITEM_PRODUTO_USE_CASE;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.DELETAR_ITEM_PRODUTO.equals(tipoJornada);
    }

    @Override
    public Void execute(Integer idItemProduto, GetJornada getRequest) {
        DELETAR_ITEM_PRODUTO_USE_CASE.execute(idItemProduto);
        return null;
    }
}
