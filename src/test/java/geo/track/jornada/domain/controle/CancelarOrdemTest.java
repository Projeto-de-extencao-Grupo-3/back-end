package geo.track.jornada.domain.controle;

import geo.track.jornada.domain.OrdemDeServicoService;
import geo.track.jornada.domain.controle.CancelarOrdem;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CancelarOrdemTest {

    @Mock
    private OrdemDeServicoService ordemDeServicoService;

    @Mock
    private OrdemDeServicoRepository ordemDeServicoRepository;

    private CancelarOrdem useCase;

    @BeforeEach
    void setUp() {
        useCase = new CancelarOrdem(ordemDeServicoService, ordemDeServicoRepository);
    }

    @Test
    void deveExecutarComSucesso() {
    }

    @Test
    void deveLancarBadBusinessRuleException_QuandoAOrdemDeServicoComStatus() {
    }

}