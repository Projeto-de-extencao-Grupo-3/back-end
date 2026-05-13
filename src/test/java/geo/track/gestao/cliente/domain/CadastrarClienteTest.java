package geo.track.gestao.cliente.domain;

import geo.track.gestao.cliente.application.contato.CadastrarContatoUseCase;
import geo.track.gestao.cliente.application.endereco.CadastrarEnderecoUseCase;
import geo.track.gestao.cliente.infraestructure.persistence.ClienteRepository;
import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.persistence.entity.TipoCliente;
import geo.track.gestao.cliente.infraestructure.request.cliente.RequestPostCliente;
import geo.track.gestao.cliente.infraestructure.request.contato.RequestPostContato;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPostEndereco;
import geo.track.gestao.oficina.domain.OficinaService;
import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarClienteTest {

    @Mock
    private ClienteService CLIENTE_SERVICE;

    @Mock
    private ClienteRepository CLIENTE_REPOSITORY;

    @Mock
    private OficinaService OFICINA_SERVICE;

    @Mock
    private CadastrarEnderecoUseCase CADASTRAR_ENDERECO_USE_CASE;

    @Mock
    private CadastrarContatoUseCase CADASTRAR_CONTATO_USE_CASE;

    @Mock
    private Log log;

    private CadastrarCliente useCase;

    @BeforeEach
    void setUp() {
        useCase = new CadastrarCliente(CLIENTE_SERVICE, CLIENTE_REPOSITORY, OFICINA_SERVICE, CADASTRAR_ENDERECO_USE_CASE, CADASTRAR_CONTATO_USE_CASE, log);
    }

    @Test
    void deveExecutarComSucesso() {
        RequestPostContato contato = new RequestPostContato("11999990000", "teste@email.com", "João", "Compras");
        RequestPostEndereco endereco = new RequestPostEndereco("01001000", "Rua A", 10, "Casa", "Centro", "São Paulo", "SP", true);
        RequestPostCliente body = new RequestPostCliente(
                "Cliente Teste",
                "12345678901",
                "123",
                TipoCliente.PESSOA_FISICA,
                1,
                List.of(contato),
                endereco
        );

        Oficina oficina = new Oficina();
        oficina.setIdOficina(1);

        Cliente salvo = new Cliente();
        salvo.setIdCliente(99);

        Cliente esperado = new Cliente();
        esperado.setIdCliente(99);

        when(CLIENTE_REPOSITORY.existsByCpfCnpjAndAtivoTrue(body.getCpfCnpj())).thenReturn(false);
        when(OFICINA_SERVICE.buscarOficinaPorId(body.getFkOficina())).thenReturn(oficina);
        when(CLIENTE_REPOSITORY.save(any(Cliente.class))).thenReturn(salvo);
        when(CLIENTE_SERVICE.buscarClientePorId(99)).thenReturn(esperado);

        Cliente retorno = useCase.execute(body);

        assertSame(esperado, retorno);
        verify(CADASTRAR_CONTATO_USE_CASE).execute(eq(99), eq(contato));
        verify(CADASTRAR_ENDERECO_USE_CASE).execute(eq(endereco), eq(99));
        verify(CLIENTE_REPOSITORY).save(any(Cliente.class));
    }

    @Test
    void deveLancarConflictException_QuandoClienteRepositoryExistsbycpfcnpjandativotrueBodyGetcpfcnpj() {
        RequestPostCliente body = new RequestPostCliente(
                "Cliente Teste",
                "12345678901",
                "123",
                TipoCliente.PESSOA_FISICA,
                1,
                List.of(new RequestPostContato("11999990000", "teste@email.com", "João", "Compras")),
                new RequestPostEndereco("01001000", "Rua A", 10, "Casa", "Centro", "São Paulo", "SP", true)
        );

        when(CLIENTE_REPOSITORY.existsByCpfCnpjAndAtivoTrue(body.getCpfCnpj())).thenReturn(true);

        assertThrows(ConflictException.class, () -> useCase.execute(body));

        verify(CLIENTE_REPOSITORY, never()).save(any(Cliente.class));
        verify(OFICINA_SERVICE, never()).buscarOficinaPorId(any());
    }

}
