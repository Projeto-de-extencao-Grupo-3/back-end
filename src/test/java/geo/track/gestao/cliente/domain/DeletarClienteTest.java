package geo.track.gestao.cliente.domain;

import geo.track.gestao.cliente.application.contato.DeletarContatoUseCase;
import geo.track.gestao.cliente.application.endereco.DeletarEnderecoUseCase;
import geo.track.gestao.cliente.infraestructure.persistence.ClienteRepository;
import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Contato;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.domain.OrdemDeServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Integer idCliente = 1;

        Contato contato = new Contato();
        contato.setIdContato(7);

        Endereco endereco = new Endereco();
        endereco.setIdEndereco(9);

        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        cliente.setAtivo(true);
        cliente.setContatos(new ArrayList<>());
        cliente.setEnderecos(new ArrayList<>());
        cliente.getContatos().add(contato);
        cliente.getEnderecos().add(endereco);

        when(ORDEM_SERVICO_SERVICE.existeOrdemServicoAbertaPorCliente(idCliente)).thenReturn(false);
        when(CLIENTE_REPOSITORY.existsById(idCliente)).thenReturn(true);
        when(CLIENTE_SERVICE.buscarClientePorId(idCliente)).thenReturn(cliente);

        useCase.execute(idCliente);

        verify(DELETAR_CONTATO_USE_CASE).execute(idCliente, 7);
        verify(DELETAR_ENDERECO_USE_CASE).execute(idCliente, 9);
        verify(CLIENTE_REPOSITORY).save(cliente);
    }

    @Test
    void deveLancarBadBusinessRuleException_QuandoOrdemServicoServiceExisteordemservicoabertaporclienteId() {
        when(ORDEM_SERVICO_SERVICE.existeOrdemServicoAbertaPorCliente(1)).thenReturn(true);

        assertThrows(BadBusinessRuleException.class, () -> useCase.execute(1));

        verify(CLIENTE_REPOSITORY, never()).save(any());
    }

    @Test
    void deveLancarDataNotFoundException_QuandoClienteRepositoryExistsbyidId() {
        when(ORDEM_SERVICO_SERVICE.existeOrdemServicoAbertaPorCliente(1)).thenReturn(false);
        when(CLIENTE_REPOSITORY.existsById(1)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> useCase.execute(1));

        verify(CLIENTE_REPOSITORY, never()).save(any());
    }

}
