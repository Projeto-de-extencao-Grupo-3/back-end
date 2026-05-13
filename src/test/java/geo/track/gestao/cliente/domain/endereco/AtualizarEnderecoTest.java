package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPutEndereco;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        RequestPutEndereco body = new RequestPutEndereco("02002000", "Rua B", 20, "Apto", "Bairro", "Cidade", "SP", false);
        Endereco endereco = new Endereco();
        endereco.setIdEndereco(2);

        when(ENDERECO_SERVICE.buscarEnderecoPorId(1, 2)).thenReturn(endereco);
        when(ENDERECO_REPOSITORY.save(endereco)).thenReturn(endereco);

        Endereco retorno = useCase.execute(1, 2, body);

        assertSame(endereco, retorno);
        assertEquals("02002000", endereco.getCep());
        verify(ENDERECO_REPOSITORY).save(endereco);
    }

}
