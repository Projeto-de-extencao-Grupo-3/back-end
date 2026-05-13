package geo.track.jornada.domain.controle;

import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.jornada.domain.OrdemDeServicoService;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        OrdemDeServico ordem = new OrdemDeServico();
        ordem.setStatus(Status.AGUARDANDO_ORCAMENTO);
        ordem.setAtivo(true);

        when(ordemDeServicoService.buscarOrdemServicoPorId(1)).thenReturn(ordem);

        useCase.execute(1);

        assertEquals(Status.CANCELADO, ordem.getStatus());
        assertFalse(ordem.getAtivo());
        verify(ordemDeServicoRepository).save(ordem);
    }

    @Test
    void deveLancarBadBusinessRuleException_QuandoStatusPodesercanceladaOrdemGetstatus() {
        OrdemDeServico ordem = new OrdemDeServico();
        ordem.setStatus(Status.EM_PRODUCAO);

        when(ordemDeServicoService.buscarOrdemServicoPorId(1)).thenReturn(ordem);

        assertThrows(BadBusinessRuleException.class, () -> useCase.execute(1));

        verify(ordemDeServicoRepository, never()).save(ordem);
    }

}
