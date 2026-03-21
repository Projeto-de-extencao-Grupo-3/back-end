package geo.track.jornada.service.usecase.implementation;

import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.entity.repository.RegistroEntradaRepository;
import geo.track.jornada.service.usecase.CadastrarEntradaUseCase;
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
