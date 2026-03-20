package geo.track.jornada.service;

import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JornadaService {
    private final List<EntradaJornadaStrategy> entradaJornadaStrategies;
    private final List<ItensJornadaStrategy> itensJornadaStrategies;

    @SuppressWarnings("unchecked")
    public RegistroEntrada realizarJornadaEntrada(GetJornada request) {
        for (EntradaJornadaStrategy strategy : entradaJornadaStrategies) {
            if (strategy.isApplicable(request.getTipoJornada())) {
                return strategy.execute(request);
            }
        }

        throw new UnsupportedOperationException("Tipo de jornada de entrada não suportado: " + request.getTipoJornada());
    }

    @SuppressWarnings("unchecked")
    public <T> T realizarJornadaItens(Integer idOrdemServico, GetJornada request) {
        for (ItensJornadaStrategy strategy : itensJornadaStrategies) {
            if (strategy.isApplicable(request.getTipoJornada())) {
                return (T) strategy.execute(idOrdemServico, request);
            }
        }

        throw new UnsupportedOperationException("Tipo de jornada de itens não suportado: " + request.getTipoJornada());
    }
}
