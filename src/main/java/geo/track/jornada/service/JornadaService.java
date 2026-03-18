package geo.track.jornada.service;

import geo.track.jornada.request.entrada.GetJornadaType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JornadaService {
    private final List<JornadaStrategy> jornadaStrategies;

    @SuppressWarnings("unchecked")
    public <T> T realizarJornada(GetJornadaType request) {
        for (JornadaStrategy strategy : jornadaStrategies) {
            if (strategy.isApplicable(request.getTipoJornada())) {
                return (T) strategy.execute(request);
            }
        }

        throw new UnsupportedOperationException("Tipo de jornada não suportado: " + request.getTipoJornada());
    }
}
