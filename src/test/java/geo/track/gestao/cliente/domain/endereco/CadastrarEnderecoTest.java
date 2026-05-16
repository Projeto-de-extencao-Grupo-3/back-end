package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.domain.ClienteService;
import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPostEndereco;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarEnderecoTest {

    @Mock
    private EnderecoRepository ENDERECO_REPOSITORY;

    @Mock
    private EnderecoService ENDERECO_SERVICE;

    @Mock
    private ClienteService CLIENTE_SERVICE;

    @Mock
    private Log log;

    private CadastrarEndereco useCase;

    @BeforeEach
    void setUp() {
        useCase = new CadastrarEndereco(ENDERECO_REPOSITORY, ENDERECO_SERVICE, CLIENTE_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
        RequestPostEndereco body = new RequestPostEndereco("01001000", "Rua A", 10, "Casa", "Centro", "São Paulo", "SP", true);
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1);

        Endereco salvo = new Endereco();
        salvo.setIdEndereco(10);

        when(ENDERECO_SERVICE.existeEnderecoPorCepENumero(body.getCep(), body.getNumero())).thenReturn(false);
        when(CLIENTE_SERVICE.buscarClientePorId(1)).thenReturn(cliente);
        when(ENDERECO_REPOSITORY.save(any(Endereco.class))).thenReturn(salvo);

        Endereco retorno = useCase.execute(body, 1);

        assertSame(salvo, retorno);
        verify(ENDERECO_REPOSITORY).save(any(Endereco.class));
    }

    @Test
    void deveLancarConflictException_QuandoEnderecoServiceExisteenderecoporcepenumeroBodyGetcep() {
        RequestPostEndereco body = new RequestPostEndereco("01001000", "Rua A", 10, "Casa", "Centro", "São Paulo", "SP", true);
        when(ENDERECO_SERVICE.existeEnderecoPorCepENumero(body.getCep(), body.getNumero())).thenReturn(true);

        assertThrows(ConflictException.class, () -> useCase.execute(body, 1));

        verify(ENDERECO_REPOSITORY, never()).save(any());
    }

}
