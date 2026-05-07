package geo.track.gestao.veiculo.domain;

import geo.track.gestao.cliente.domain.ClienteService;
import geo.track.gestao.cliente.infraestructure.persistence.ClienteRepository;
import geo.track.gestao.veiculo.domain.CadastrarVeiculo;
import geo.track.gestao.veiculo.infraestructure.persistence.VeiculoRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    }

    @Test
    void deveLancarConflictException_QuandoRegraDeNegocioViolada() {
    }

}