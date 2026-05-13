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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        OrdemDeServico ordem = new OrdemDeServico();
        ordem.setStatus(Status.AGUARDANDO_ORCAMENTO);

        when(ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(1)).thenReturn(ordem);
        when(ORDEM_SERVICO_REPOSITORY.save(ordem)).thenReturn(ordem);

        OrdemDeServico retorno = useCase.execute(1, Status.AGUARDANDO_AUTORIZACAO);

        assertSame(ordem, retorno);
        assertEquals(Status.AGUARDANDO_AUTORIZACAO, retorno.getStatus());
        assertEquals(LocalDate.now(), retorno.getDataAtualizacao());
        verify(ORDEM_SERVICO_REPOSITORY).save(ordem);
    }

    @Test
    void deveLancarBadBusinessRuleException_QuandoStatusatualEqualsStatusCancelado() {
        OrdemDeServico ordem = new OrdemDeServico();
        ordem.setStatus(Status.CANCELADO);

        when(ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(1)).thenReturn(ordem);

        assertThrows(BadBusinessRuleException.class, () -> useCase.execute(1, Status.AGUARDANDO_ORCAMENTO));
    }

    @Test
    void deveLancarBadBusinessRuleException_QuandoStatusatualOrdinal() {
        OrdemDeServico ordem = new OrdemDeServico();
        ordem.setStatus(Status.FINALIZADO);

        when(ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(1)).thenReturn(ordem);

        assertThrows(BadBusinessRuleException.class, () -> useCase.execute(1, Status.CANCELADO));
    }

    @Test
    void deveLancarBadBusinessRuleException_QuandoStatusatualEqualsStatusdesejado() {
        OrdemDeServico ordem = new OrdemDeServico();
        ordem.setStatus(Status.AGUARDANDO_ORCAMENTO);

        when(ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(1)).thenReturn(ordem);

        assertThrows(BadBusinessRuleException.class, () -> useCase.execute(1, Status.AGUARDANDO_ORCAMENTO));
    }

}
