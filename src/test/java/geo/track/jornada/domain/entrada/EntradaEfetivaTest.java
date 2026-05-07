package geo.track.jornada.domain.entrada;

import geo.track.gestao.veiculo.domain.VeiculoService;
import geo.track.jornada.application.CadastrarEntradaUseCase;
import geo.track.jornada.application.CadastrarOrdemServicoUseCase;
import geo.track.jornada.domain.entrada.EntradaEfetiva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EntradaEfetivaTest {

    @Mock
    private CadastrarOrdemServicoUseCase CADASTRAR_ORDEM_PORT;

    @Mock
    private CadastrarEntradaUseCase CADASTRAR_ENTRADA_PORT;

    @Mock
    private VeiculoService VEICULO_SERVICE;

    private EntradaEfetiva useCase;

    @BeforeEach
    void setUp() {
        useCase = new EntradaEfetiva(CADASTRAR_ORDEM_PORT, CADASTRAR_ENTRADA_PORT, VEICULO_SERVICE);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}