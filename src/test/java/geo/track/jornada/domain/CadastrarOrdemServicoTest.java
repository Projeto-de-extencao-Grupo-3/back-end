package geo.track.jornada.domain;

import geo.track.jornada.domain.CadastrarOrdemServico;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CadastrarOrdemServicoTest {

    @Mock
    private OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Mock
    private OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    private CadastrarOrdemServico useCase;

    @BeforeEach
    void setUp() {
        useCase = new CadastrarOrdemServico(ORDEM_SERVICO_REPOSITORY, ORDEM_SERVICO_SERVICE);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}