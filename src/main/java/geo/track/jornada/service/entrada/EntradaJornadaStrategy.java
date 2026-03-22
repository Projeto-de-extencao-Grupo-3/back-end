package geo.track.jornada.service.entrada;

import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.service.base.JornadaTypeStrategy;

/**
 * Strategy específica para operações de Jornada de ENTRADA.
 *
 * Esta interface define o contrato para todas as implementações relacionadas
 * ao processo de ENTRADA de veículos na oficina, como:
 *
 * - **AGENDAMENTO:** Agendamento futuro de entrada do veículo
 * - **CONFIRMAR_ENTRADA_AGENDADA:** Confirmação de entrada previamente agendada
 * - **ENTRADA_EFETIVA:** Entrada com ordem de serviço já criada
 * - **ENTRADA_EFETIVA_SEM_CADASTRO:** Entrada sem cadastro prévio
 *
 * Cada implementação é responsável por gerenciar o ciclo de vida completo
 * de um RegistroEntrada específico para seu tipo de jornada.
 *
 * @see TipoJornada para tipos suportados
 * @see EntradaService para orquestração das estratégias
 */
public interface EntradaJornadaStrategy extends JornadaTypeStrategy<TipoJornada> {

    /**
     * Verifica se esta strategy é aplicável para o tipo de jornada fornecido
     *
     * @param tipoJornada tipo de jornada de entrada
     * @return true se esta strategy deve processar este tipo
     */
    @Override
    Boolean isApplicable(TipoJornada tipoJornada);

    /**
     * Executa a lógica de entrada específica para este tipo de jornada
     *
     * @param request implementação de GetJornada com os dados da entrada
     *                Deve ser castado para o tipo específico da strategy
     * @return RegistroEntrada criado ou atualizado
     */
    RegistroEntrada execute(GetJornada request);
}

