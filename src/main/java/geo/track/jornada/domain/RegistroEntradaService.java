package geo.track.jornada.domain;

import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.RegistroEntradaExceptionMessages;
import geo.track.jornada.infraestructure.persistence.RegistroEntradaRepository;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RegistroEntradaService{
    private final RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;
    private final Log log;

    public RegistroEntrada buscarEntradaPorId(Integer idRegistro){
        log.info("Buscando registro de entrada ID: {}", idRegistro);
        Optional<RegistroEntrada> registro = REGISTRO_ENTRADA_REPOSITORY.findById(idRegistro);

        if (registro.isEmpty()){
            throw new DataNotFoundException(RegistroEntradaExceptionMessages.REGISTRO_ENTRADA_NAO_ENCONTRADO, Domains.REGISTRO_ENTRADA);
        }

        return registro.get();
    }
}
