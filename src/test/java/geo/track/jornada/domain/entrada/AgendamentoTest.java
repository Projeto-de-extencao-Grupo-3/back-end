package geo.track.jornada.domain.entrada;

import geo.track.gestao.veiculo.domain.VeiculoService;
import geo.track.jornada.application.CadastrarOrdemServicoUseCase;
import geo.track.jornada.domain.entrada.Agendamento;
import geo.track.jornada.infraestructure.persistence.RegistroEntradaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AgendamentoTest {

    @Mock
    private CadastrarOrdemServicoUseCase CADASTRAR_OS_PORT;

    @Mock
    private RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;

    @Mock
    private VeiculoService VEICULO_SERVICE;

    private Agendamento useCase;

    @BeforeEach
    void setUp() {
        useCase = new Agendamento(CADASTRAR_OS_PORT, REGISTRO_ENTRADA_REPOSITORY, VEICULO_SERVICE);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}