package geo.track.jornada.service;

import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.service.entrada.EntradaJornadaStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Orquestrador de Strategy para Jornada de ENTRADA.
 * Utiliza o padrão de Type-Based Strategy Selection para mapear diferentes tipos
 * de entrada de veículos para suas respectivas estratégias.
 * - Responsável por operações de ENTRADA de veículos
 * - Suporta:
 *   - AGENDAMENTO: Agendamento de entrada futura
 *   - CONFIRMAR_ENTRADA_AGENDADA: Confirmação de entrada previamente agendada
 *   - ENTRADA_EFETIVA: Entrada com cadastro prévio
 *   - ENTRADA_EFETIVA_SEM_CADASTRO: Entrada sem ordem de serviço prévia
 *
 * @see TipoJornada para tipos de entrada suportados
 */
@Service
@RequiredArgsConstructor
public class EntradaService {
    private final List<EntradaJornadaStrategy> entradaJornadaStrategies;

    /**
     * Executa a strategy de entrada apropriada baseada no tipo de jornada
     * @param request implementação de GetJornada com tipo de jornada indicado
     * @return RegistroEntrada criado ou atualizado
     * @throws UnsupportedOperationException se tipo de jornada não for suportado
     */
    public RegistroEntrada realizarJornadaEntrada(GetJornada request) {
        TipoJornada tipoJornada = request.getTipoJornada();

        for (EntradaJornadaStrategy strategy : entradaJornadaStrategies) {
            if (strategy.isApplicable(tipoJornada)) {
                return strategy.execute(request);
            }
        }

        throw new UnsupportedOperationException("Tipo de jornada de entrada não suportado: " + tipoJornada);
    }

}
