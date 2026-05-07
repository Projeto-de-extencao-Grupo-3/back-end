package geo.track.jornada.domain.controle;

import geo.track.jornada.domain.OrdemDeServicoService;
import geo.track.jornada.domain.controle.AtualizarStatus;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AtualizarStatusTest {

    @Mock
    private OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @Mock
    private OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    private AtualizarStatus useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarStatus(ORDEM_SERVICO_SERVICE, ORDEM_SERVICO_REPOSITORY);
    }

    @Test
    void deveExecutarComSucesso() {
    }

    @Test
    void deveLancarBadBusinessRuleException_QuandoNaoEPossivelAlterarOStatusDeUma() {
    }

    @Test
    void deveLancarBadBusinessRuleException_QuandoNaoEPossivelAlterarOStatusDeUma2() {
    }

    @Test
    void deveLancarBadBusinessRuleException_QuandoTransicaoDeStatusInvalidaOProximoStatusDeve() {
    }

}