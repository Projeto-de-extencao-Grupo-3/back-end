package geo.track.jornada.domain.controle;

import geo.track.infraestructure.log.Log;
import geo.track.jornada.domain.OrdemDeServicoService;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefinirDataSaidaPrevistaTest {

    @Mock
    private OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @Mock
    private OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Mock
    private Log log;

    private DefinirDataSaidaPrevista useCase;

    @BeforeEach
    void setUp() {
        useCase = new DefinirDataSaidaPrevista(ORDEM_SERVICO_SERVICE, ORDEM_SERVICO_REPOSITORY, log);
    }

    @Test
    void deveExecutarComSucesso() {
        LocalDate dataPrevista = LocalDate.now().plusDays(2);
        OrdemDeServico ordem = new OrdemDeServico();

        when(ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(1)).thenReturn(ordem);
        when(ORDEM_SERVICO_REPOSITORY.save(ordem)).thenReturn(ordem);

        OrdemDeServico retorno = useCase.execute(1, dataPrevista);

        assertSame(ordem, retorno);
        assertEquals(dataPrevista, retorno.getDataSaidaPrevista());
        verify(ORDEM_SERVICO_REPOSITORY).save(ordem);
    }

}
