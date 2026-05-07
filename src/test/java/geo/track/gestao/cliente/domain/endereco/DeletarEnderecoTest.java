package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeletarEnderecoTest {

    @Mock
    private EnderecoService ENDERECO_SERVICE;

    @Mock
    private EnderecoRepository ENDERECO_REPOSITORY;

    private DeletarEndereco useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeletarEndereco(ENDERECO_SERVICE, ENDERECO_REPOSITORY);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}
