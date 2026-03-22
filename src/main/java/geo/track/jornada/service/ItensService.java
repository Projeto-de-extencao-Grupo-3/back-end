package geo.track.jornada.service;

import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.service.itens.ItensJornadaStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Orquestrador de Strategy para Jornada de ITENS.
 * Utiliza o padrão de Type-Based Strategy Selection para mapear diferentes tipos
 * de operações sobre itens (produtos ou serviços) em ordens de serviço.
 * **Subdomínio:** ITENS
 * - Responsável por operações relacionadas a ADIÇÃO e SAÍDA de itens
 * - Suporta:
 *   - ADICIONAR_ITEM_PRODUTO: Adição de produto ao orçamento
 *   - ADICIONAR_ITEM_SERVICO: Adição de serviço ao orçamento
 *   - REALIZAR_SAIDA_MATERIAL: Saída de material do estoque
 *
 * @see TipoJornada para tipos de operação de itens suportados
 */
@Service
@RequiredArgsConstructor
public class ItensService {
    @SuppressWarnings("rawtypes")
    private final List<ItensJornadaStrategy> itensJornadaStrategies;

    /**
     * Executa a strategy de itens apropriada baseada no tipo de jornada
     * @param idOrdemServico ID da ordem de serviço relacionada
     * @param request implementação de GetJornada com tipo de jornada indicado
     * @param <T> tipo de retorno da strategy (pode ser request ou response)
     * @return resultado da execução da strategy de itens
     * @throws UnsupportedOperationException se tipo de jornada não for suportado
     */
    @SuppressWarnings("unchecked")
    public <T> T realizarJornadaItens(Integer idOrdemServico, GetJornada request) {
        TipoJornada tipoJornada = request.getTipoJornada();

        for (ItensJornadaStrategy strategy : itensJornadaStrategies) {
            if (strategy.isApplicable(tipoJornada)) {
                return (T) strategy.execute(idOrdemServico, request);
            }
        }

        throw new UnsupportedOperationException("Tipo de jornada de itens não suportado: " + tipoJornada);
    }
}
