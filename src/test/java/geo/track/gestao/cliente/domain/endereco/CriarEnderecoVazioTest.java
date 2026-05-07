package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.domain.ClienteService;
import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CriarEnderecoVazioTest {

    @Mock
    private EnderecoRepository ENDERECO_REPOSITORY;

    @Mock
    private ClienteService CLIENTE_SERVICE;

    @Mock
    private Log log;

    private CriarEnderecoVazio useCase;

    @BeforeEach
    void setUp() {
        useCase = new CriarEnderecoVazio(ENDERECO_REPOSITORY, CLIENTE_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}
