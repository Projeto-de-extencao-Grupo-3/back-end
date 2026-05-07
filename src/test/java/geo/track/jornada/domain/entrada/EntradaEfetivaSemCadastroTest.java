package geo.track.jornada.domain.entrada;

import geo.track.gestao.veiculo.application.CadastrarVeiculoUseCase;
import geo.track.jornada.application.CadastrarEntradaUseCase;
import geo.track.jornada.application.CadastrarOrdemServicoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EntradaEfetivaSemCadastroTest {

    @Mock
    private CadastrarOrdemServicoUseCase CADASTRAR_ORDEM_PORT;

    @Mock
    private CadastrarEntradaUseCase CADASTRAR_ENTRADA_PORT;

    @Mock
    private CadastrarVeiculoUseCase CADASTRAR_VEICULO_PORT;

    private EntradaEfetivaSemCadastro useCase;

    @BeforeEach
    void setUp() {
        useCase = new EntradaEfetivaSemCadastro(CADASTRAR_ORDEM_PORT, CADASTRAR_ENTRADA_PORT, CADASTRAR_VEICULO_PORT);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}
