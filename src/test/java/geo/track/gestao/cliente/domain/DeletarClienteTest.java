package geo.track.gestao.cliente.domain;

import geo.track.gestao.cliente.application.contato.DeletarContatoUseCase;
import geo.track.gestao.cliente.application.endereco.DeletarEnderecoUseCase;
import geo.track.gestao.cliente.infraestructure.persistence.ClienteRepository;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.domain.OrdemDeServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeletarClienteTest {

    @Mock
    private ClienteRepository CLIENTE_REPOSITORY;

    @Mock
    private ClienteService CLIENTE_SERVICE;

    @Mock
    private OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @Mock
    private DeletarContatoUseCase DELETAR_CONTATO_USE_CASE;

    @Mock
    private DeletarEnderecoUseCase DELETAR_ENDERECO_USE_CASE;

    @Mock
    private Log log;

    private DeletarCliente useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeletarCliente(CLIENTE_REPOSITORY, CLIENTE_SERVICE, ORDEM_SERVICO_SERVICE, DELETAR_CONTATO_USE_CASE, DELETAR_ENDERECO_USE_CASE, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

    @Test
    void deveLancarBadBusinessRuleException_QuandoRegraDeNegocioViolada() {
    }

    @Test
    void deveLancarDataNotFoundException_QuandoRegraDeNegocioViolada() {
    }

}
