package geo.track.gestao.cliente.domain.contato;

import geo.track.gestao.cliente.infraestructure.persistence.ContatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    }

}
