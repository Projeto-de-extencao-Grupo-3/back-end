package geo.track.jornada.service.itens;

import geo.track.gestao.service.produto.RealizarBaixaEstoqueItemProdutoUseCase;
import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.gestao.entity.ItemProduto;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.gestao.service.ItemProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Strategy para ADICIONAR SERVICO AO ORÇAMENTO
 **/
@Component
@RequiredArgsConstructor
public class RealizarSaidaMaterialStrategy implements ItensJornadaStrategy {
    private final ItemProdutoService ITEM_PRODUTO_SERVICE;
    private final RealizarBaixaEstoqueItemProdutoUseCase REALIZAR_BAIXA_ESTOQUE_ITEM_PRODUTO_USE_CASE;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.SAIDA_MATERIAL.equals(tipoJornada);
    }

    @Override
    public ItemProduto execute(Integer idItemProduto, GetJornada getRequest) {
        if (REALIZAR_BAIXA_ESTOQUE_ITEM_PRODUTO_USE_CASE.execute(idItemProduto)) {
            return ITEM_PRODUTO_SERVICE.buscarRegistroPorID(idItemProduto);
        } else {
            throw new BadBusinessRuleException("Não foi possível realizar a saída do material. Verifique o estoque disponível.", Domains.ITEM_SERVICO);
        }
    }
}
