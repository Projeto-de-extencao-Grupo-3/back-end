package geo.track.jornada.service;

import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.service.controle.ControleJornadaStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Orquestrador de Strategy para Controle de Jornadas.
 * - Responsável por operações de controle de componentes relacionado a jornadas, como controle de ordens de serviço, controle de status, etc.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@org.springframework.beans.factory.annotation.Autowired(required = false)})
public class ControleService {
    private final List<ControleJornadaStrategy> controleJornadaStrategies;

    /**
     * Executa a strategy de controle apropriada baseada no tipo de jornada
     * @param request implementação de GetJornada com tipo de jornada indicado
     * @return resultado da operação de controle
     * @throws UnsupportedOperationException se tipo de jornada não for suportado
     */
    public <T> T realizarJornadaControle(Integer id, GetJornada request) {
        if (controleJornadaStrategies == null || controleJornadaStrategies.isEmpty()) {
            throw new UnsupportedOperationException("Nenhuma estratégia de controle disponível");
        }

        for (ControleJornadaStrategy strategy : controleJornadaStrategies) {
            if (strategy.isApplicable(request.getTipoJornada())) {
                return (T) strategy.execute(id, request);
            }
        }

        throw new UnsupportedOperationException("Tipo de jornada de controle não suportado: " + request.getTipoJornada());
    }
}
