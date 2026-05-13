package geo.track.gestao.cliente.domain.contato;

import geo.track.gestao.cliente.infraestructure.persistence.ContatoRepository;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Contato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeletarContatoTest {

    @Mock
    private ContatoRepository CONTATO_REPOSITORY;

    @Mock
    private ContatoService CONTATO_SERVICE;

    private DeletarContato useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeletarContato(CONTATO_REPOSITORY, CONTATO_SERVICE);
    }

    @Test
    void deveExecutarComSucesso() {
        Contato contato = new Contato();
        contato.setIdContato(2);

        when(CONTATO_SERVICE.buscarContatoPorId(1, 2)).thenReturn(contato);

        useCase.execute(1, 2);

        verify(CONTATO_REPOSITORY).delete(contato);
    }

}
