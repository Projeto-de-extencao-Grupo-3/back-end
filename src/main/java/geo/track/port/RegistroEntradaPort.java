package geo.track.port;

import geo.track.domain.RegistroEntrada;
import geo.track.dto.registroEntrada.request.RequestPostEntrada;
import geo.track.dto.registroEntrada.request.RequestPostEntradaAgendada;
import geo.track.dto.registroEntrada.request.RequestPutRegistroEntrada;

import java.util.List;

public interface RegistroEntradaPort {
    RegistroEntrada realizarAgendamentoVeiculo(RequestPostEntradaAgendada dto);
    RegistroEntrada realizarEntradaVeiculo(RequestPostEntrada dto);
    RegistroEntrada atualizarEntradaVeiculoAgendado(RequestPutRegistroEntrada dto);
    RegistroEntrada postRegistro(RequestPostEntrada body);
    List<RegistroEntrada> findRegistros();
    RegistroEntrada findRegistroById(Integer idRegistro);
    RegistroEntrada putRegistro(RequestPutRegistroEntrada registroDTO);
    void deletarRegistro(Integer idRegistro);
}
