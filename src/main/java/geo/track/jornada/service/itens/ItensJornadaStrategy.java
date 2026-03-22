package geo.track.jornada.service.itens;

import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.service.base.JornadaTypeStrategy;

/**
 * Strategy específica para operações de Jornada de ITENS.
 *
 * Esta interface define o contrato para todas as implementações relacionadas
 * ao gerenciamento de ITENS (produtos ou serviços) em ordens de serviço, como:
 *
 * - **ADICIONAR_ITEM_PRODUTO:** Adição de produto ao orçamento/ordem
 * - **ADICIONAR_ITEM_SERVICO:** Adição de serviço ao orçamento/ordem
 * - **REALIZAR_SAIDA_MATERIAL:** Saída de material do estoque durante execução
 *
 * Cada implementação é responsável por:
 * 1. Validar os dados de entrada
 * 2. Atualizar os registros de itens apropriados
 * 3. Retornar o resultado da operação (pode ser request ou response)
 *
 * @see TipoJornada para tipos suportados
 * @see ItensService para orquestração das estratégias
 */
public interface ItensJornadaStrategy extends JornadaTypeStrategy<TipoJornada> {

    /**
     * Verifica se esta strategy é aplicável para o tipo de jornada fornecido
     *
     * @param tipoJornada tipo de jornada de itens
     * @return true se esta strategy deve processar este tipo
     */
    @Override
    Boolean isApplicable(TipoJornada tipoJornada);

    /**
     * Executa a lógica de manipulação de itens específica para este tipo de jornada
     *
     * @param idOrdemServico ID da ordem de serviço a qual os itens pertencem
     * @param request implementação de GetJornada com os dados da operação
     *                Deve ser castado para o tipo específico da strategy
     * @return resultado da operação (pode ser RequestAdicionarItemProduto,
     *         RequestAdicionarItemServico, ResponseSaidaMaterial, etc)
     */
    Object execute(Integer idOrdemServico, GetJornada request);
}

