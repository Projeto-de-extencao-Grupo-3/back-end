package geo.track.gestao.veiculo.domain;

import geo.track.gestao.veiculo.infraestructure.persistence.VeiculoRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlterarCorVeiculoTest {

    @Mock
    private VeiculoRepository VEICULO_REPOSITORY;

    @Mock
    private VeiculoService VEICULO_SERVICE;

    @Mock
    private Log log;

    private AlterarCorVeiculo useCase;

    @BeforeEach
    void setUp() {
        useCase = new AlterarCorVeiculo(VEICULO_REPOSITORY, VEICULO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}
