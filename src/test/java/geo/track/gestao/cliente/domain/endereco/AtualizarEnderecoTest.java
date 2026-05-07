package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.domain.endereco.AtualizarEndereco;
import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AtualizarEnderecoTest {

    @Mock
    private EnderecoRepository ENDERECO_REPOSITORY;

    @Mock
    private EnderecoService ENDERECO_SERVICE;

    @Mock
    private Log log;

    private AtualizarEndereco useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarEndereco(ENDERECO_REPOSITORY, ENDERECO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}