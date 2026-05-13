package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.domain.ClienteService;
import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1);

        Endereco salvo = new Endereco();
        salvo.setIdEndereco(10);

        when(CLIENTE_SERVICE.buscarClientePorId(1)).thenReturn(cliente);
        when(ENDERECO_REPOSITORY.save(any(Endereco.class))).thenReturn(salvo);

        Endereco retorno = useCase.execute(1);

        assertSame(salvo, retorno);
        verify(ENDERECO_REPOSITORY).save(any(Endereco.class));
    }

}
