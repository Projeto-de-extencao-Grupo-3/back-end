package geo.track.gestao.cliente.domain.contato;

import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Contato;
import geo.track.gestao.cliente.infraestructure.persistence.ContatoRepository;
import geo.track.gestao.cliente.application.contato.DeletarContatoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarContato implements DeletarContatoUseCase {
    private final ContatoRepository CONTATO_REPOSITORY;
    private final ContatoService CONTATO_SERVICE;

    @Override
    public void execute(Integer idCliente, Integer idContato) {
        Contato contato = CONTATO_SERVICE.buscarContatoPorId(idCliente, idContato);
        CONTATO_REPOSITORY.delete(contato);
    }
}

