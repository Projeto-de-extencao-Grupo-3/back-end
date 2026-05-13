package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Endereco endereco = new Endereco();
        endereco.setIdEndereco(2);

        when(ENDERECO_SERVICE.buscarEnderecoPorId(1, 2)).thenReturn(endereco);

        useCase.execute(1, 2);

        verify(ENDERECO_REPOSITORY).delete(endereco);
    }

}
