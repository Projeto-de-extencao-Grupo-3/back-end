package geo.track.gestao.service.contato.implementations;

import geo.track.gestao.entity.Contato;
import geo.track.gestao.entity.repository.ContatoRepository;
import geo.track.gestao.service.ContatoService;
import geo.track.gestao.service.contato.DeletarContatoUseCase;
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

