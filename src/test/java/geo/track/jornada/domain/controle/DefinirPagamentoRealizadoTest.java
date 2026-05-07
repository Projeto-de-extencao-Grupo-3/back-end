package geo.track.jornada.domain.controle;

import geo.track.jornada.domain.OrdemDeServicoService;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefinirPagamentoRealizadoTest {

    @Mock
    private OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @Mock
    private OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    private DefinirPagamentoRealizado useCase;

    @BeforeEach
    void setUp() {
        useCase = new DefinirPagamentoRealizado(ORDEM_SERVICO_SERVICE, ORDEM_SERVICO_REPOSITORY);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}
