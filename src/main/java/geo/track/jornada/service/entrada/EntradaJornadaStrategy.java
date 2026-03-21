package geo.track.jornada.service.entrada;

import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.enums.TipoJornada;

/**
 * Strategy específica para operações de Jornada de Entrada.

 * Esta interface define o contrato para todas as implementações relacionadas
 * ao processo de ENTRADA de veículos, como:
 * - Agendamento
 * - Confirmação de entrada agendada
 * - Entrada efetiva
 * - Entrada efetiva sem cadastro
 *
 * @see TipoJornada (AGENDAMENTO, CONFIRMAR_ENTRADA_AGENDADA, ENTRADA_EFETIVA, ENTRADA_EFETIVA_SEM_CADASTRO)
 */
public interface EntradaJornadaStrategy {

    /**
     * @param tipoJornada - recebe o tipo de jornada desejada
     * @return se a strategy é válida para executar a lógica
     */
    Boolean isApplicable(TipoJornada tipoJornada);

    /**
     * @param request o request que implementa GetJornada, com os dados da entrada
     * @return RegistroEntrada criado ou atualizado
     */
    RegistroEntrada execute(GetJornada request);
}

