package geo.track.jornada.service.controle;

import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.service.ControleService;
import geo.track.jornada.service.base.JornadaTypeStrategy;

/**
 * Strategy específica para operações de Jornada de CONTROLE.
 *
 * Esta interface define o contrato para futuras implementações relacionadas
 * ao processo de CONTROLE e MONITORAMENTO de jornadas, como:
 *
 * - Acompanhamento de progresso de ordens
 * - Validações de sequência de jornadas
 * - Relatórios de controle e auditoria
 * - Transições de estado validadas
 *
 * NOTA: Este subdomínio está em fase de desenvolvimento.
 * As estratégias específicas serão adicionadas conforme a evolução do projeto.
 *
 * @see TipoJornada para tipos que podem ser suportados
 * @see ControleService para orquestração das estratégias
 */
public interface ControleJornadaStrategy <T> extends JornadaTypeStrategy<TipoJornada> {

    /**
     * Verifica se esta strategy é aplicável para o tipo de jornada fornecido
     *
     * @param tipoJornada tipo de jornada de controle
     * @return true se esta strategy deve processar este tipo
     */
    @Override
    Boolean isApplicable(TipoJornada tipoJornada);

    /**
     * Executa a lógica de controle específica para este tipo de jornada
     *
     * @param request implementação de GetJornada com os dados da operação de controle
     *                Deve ser castado para o tipo específico da strategy
     * @return resultado da operação de controle
     */
    T execute(Integer id, GetJornada request);
}
