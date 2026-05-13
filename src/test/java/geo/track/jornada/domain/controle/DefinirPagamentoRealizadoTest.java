package geo.track.jornada.domain.controle;

import geo.track.jornada.domain.OrdemDeServicoService;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        OrdemDeServico ordem = new OrdemDeServico();
        ordem.setPagtRealizado(false);

        when(ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(1)).thenReturn(ordem);
        when(ORDEM_SERVICO_REPOSITORY.save(ordem)).thenReturn(ordem);

        OrdemDeServico retorno = useCase.execute(1);

        assertSame(ordem, retorno);
        assertTrue(retorno.getPagtRealizado());
        verify(ORDEM_SERVICO_REPOSITORY).save(ordem);
    }

}
