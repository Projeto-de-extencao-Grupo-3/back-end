package geo.track.jornada.domain.entrada;

import geo.track.jornada.application.CadastrarEntradaUseCase;
import geo.track.jornada.domain.RegistroEntradaService;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConfirmacaoTest {

    @Mock
    private OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Mock
    private CadastrarEntradaUseCase CADASTRAR_ENTRADA_PORT;

    @Mock
    private RegistroEntradaService REGISTRO_ENTRADA_SERVICE;

    private Confirmacao useCase;

    @BeforeEach
    void setUp() {
        useCase = new Confirmacao(ORDEM_SERVICO_REPOSITORY, CADASTRAR_ENTRADA_PORT, REGISTRO_ENTRADA_SERVICE);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}
