package geo.track.jornada.domain;

import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;
import geo.track.jornada.infraestructure.persistence.RegistroEntradaRepository;
import geo.track.jornada.application.CadastrarEntradaUseCase;
import org.springframework.stereotype.Component;

@Component
public class CadastrarEntradaImplementation implements CadastrarEntradaUseCase {
    private final RegistroEntradaRepository ENTRADA_REPOSITORY;

    public CadastrarEntradaImplementation(RegistroEntradaRepository ordemDeServicoRepository) {
        this.ENTRADA_REPOSITORY = ordemDeServicoRepository;
    }

    public RegistroEntrada execute(RegistroEntrada entity) {
        return ENTRADA_REPOSITORY.save(entity);
    }

}
