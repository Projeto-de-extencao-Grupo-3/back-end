package geo.track.jornada.domain;

import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        OrdemDeServico ordemSalva = new OrdemDeServico();
        ordemSalva.setIdOrdemServico(10);

        when(ORDEM_SERVICO_REPOSITORY.save(any(OrdemDeServico.class))).thenReturn(ordemSalva);

        OrdemDeServico retorno = useCase.execute(Status.AGUARDANDO_ENTRADA, 1);

        assertSame(ordemSalva, retorno);
        verify(ORDEM_SERVICO_SERVICE).existeOrdemServicoAbertaPorVeiculo(1);
        verify(ORDEM_SERVICO_REPOSITORY).save(any(OrdemDeServico.class));
    }

    @Test
    void devePropagarBadBusinessRuleException_QuandoExisteOrdemAbertaPorVeiculo() {
        doThrow(new BadBusinessRuleException("erro", Domains.ORDEM_DE_SERVICO))
                .when(ORDEM_SERVICO_SERVICE).existeOrdemServicoAbertaPorVeiculo(1);

        assertThrows(BadBusinessRuleException.class, () -> useCase.execute(Status.AGUARDANDO_ENTRADA, 1));

        verify(ORDEM_SERVICO_REPOSITORY, never()).save(any());
    }

}
