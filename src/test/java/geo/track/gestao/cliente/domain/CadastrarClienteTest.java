package geo.track.gestao.cliente.domain;

import geo.track.gestao.cliente.application.contato.CadastrarContatoUseCase;
import geo.track.gestao.cliente.application.endereco.CadastrarEnderecoUseCase;
import geo.track.gestao.cliente.infraestructure.persistence.ClienteRepository;
import geo.track.gestao.oficina.domain.OficinaService;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    }

    @Test
    void deveLancarConflictException_QuandoClienteRepositoryExistsbycpfcnpjandativotrueBodyGetcpfcnpj() {
    }

}
