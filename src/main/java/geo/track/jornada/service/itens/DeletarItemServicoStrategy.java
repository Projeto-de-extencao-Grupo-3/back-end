package geo.track.jornada.service.itens;

import geo.track.dto.itensServicos.RequestPutItemServico;
import geo.track.gestao.entity.ItemServico;
import geo.track.gestao.service.itemservico.DeletarItemServicoUseCase;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ADICIONAR SERVICO AO ORÇAMENTO **/
@Component
@RequiredArgsConstructor
public class DeletarItemServicoStrategy implements ItensJornadaStrategy {
    private final DeletarItemServicoUseCase DELETAR_ITEM_SERVICO_USE_CASE;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.DELETAR_ITEM_SERVICO.equals(tipoJornada);
    }

    @Override
    public Void execute(Integer idItemServico, GetJornada getRequest) {
        DELETAR_ITEM_SERVICO_USE_CASE.execute(idItemServico);
        return null;
    }
}
