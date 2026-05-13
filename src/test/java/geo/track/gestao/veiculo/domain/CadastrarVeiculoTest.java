package geo.track.gestao.veiculo.domain;

import geo.track.gestao.cliente.domain.ClienteService;
import geo.track.gestao.cliente.infraestructure.persistence.ClienteRepository;
import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.veiculo.infraestructure.persistence.VeiculoRepository;
import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.veiculo.infraestructure.request.RequestPostVeiculo;
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
class CadastrarVeiculoTest {

    @Mock
    private VeiculoRepository VEICULO_REPOSITORY;

    @Mock
    private ClienteRepository CLIENTE_REPOSITORY;

    @Mock
    private ClienteService CLIENTE_SERVICE;

    @Mock
    private Log log;

    private CadastrarVeiculo useCase;

    @BeforeEach
    void setUp() {
        useCase = new CadastrarVeiculo(VEICULO_REPOSITORY, CLIENTE_REPOSITORY, CLIENTE_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
        RequestPostVeiculo body = new RequestPostVeiculo();
        body.setPlaca("BRA2E19");
        body.setMarca("VW");
        body.setModelo("Nivus");
        body.setPrefixo("1200");
        body.setAnoModelo(2023);
        body.setIdCliente(1);

        Cliente cliente = new Cliente();
        cliente.setIdCliente(1);

        Veiculo salvo = new Veiculo();
        salvo.setIdVeiculo(10);

        when(VEICULO_REPOSITORY.existsByPlacaIgnoreCase(body.getPlaca())).thenReturn(false);
        when(CLIENTE_SERVICE.buscarClientePorId(1)).thenReturn(cliente);
        when(VEICULO_REPOSITORY.save(any(Veiculo.class))).thenReturn(salvo);

        Veiculo retorno = useCase.execute(body);

        assertSame(salvo, retorno);
        verify(VEICULO_REPOSITORY).save(any(Veiculo.class));
    }

    @Test
    void deveLancarConflictException_QuandoVeiculoRepositoryExistsbyplacaignorecaseBodyGetplaca() {
        RequestPostVeiculo body = new RequestPostVeiculo();
        body.setPlaca("BRA2E19");

        when(VEICULO_REPOSITORY.existsByPlacaIgnoreCase(body.getPlaca())).thenReturn(true);

        assertThrows(ConflictException.class, () -> useCase.execute(body));

        verify(VEICULO_REPOSITORY, never()).save(any());
    }

}
