package geo.track.jornada.service.listagem;

import geo.track.jornada.request.ListagemJornadaParams;
import geo.track.jornada.response.listagem.ListagemJornadaResponse;

/**
 * Strategy específica para operações de Jornada de LISTAGEM.
 *
 * Esta interface define o contrato para todas as implementações relacionadas
 * à CONSULTA e VISUALIZAÇÃO de ordens de serviço, como:
 *
 * - **LISTAGEM_SIMPLES:** Lista básica de todas as ordens
 * - **PAINEL_CONTROLE:** Visualização com resumo de status
 * - **ANALISE_FINANCEIRA:** Análise de dados financeiros das ordens
 *
 * Cada implementação é responsável por:
 * 1. Validar os parâmetros de entrada
 * 2. Construir a consulta apropriada (simples, com aggregations, etc)
 * 3. Retornar os dados formatados no padrão ListagemJornadaResponse
 *
 * @see ListagemJornadaParams para parâmetros de entrada
 * @see ListagemJornadaResponse para formato de resposta
 * @see ListagemService para orquestração das estratégias
 */
public interface ListagemJornadaStrategy {

    /**
     * Executa a lógica de listagem específica para este tipo de consulta
     *
     * @param request parâmetros de listagem indicando tipo e filtros
     * @return ListagemJornadaResponse com os dados formatados
     */
    ListagemJornadaResponse execute(ListagemJornadaParams request);

}
