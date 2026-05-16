package geo.track.jornada.domain.entrada;

import geo.track.gestao.veiculo.domain.VeiculoService;
import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.jornada.application.CadastrarOrdemServicoUseCase;
import geo.track.jornada.infraestructure.persistence.RegistroEntradaRepository;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgendamentoTest {

    @Mock
    private CadastrarOrdemServicoUseCase CADASTRAR_OS_PORT;

    @Mock
    private RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;

    @Mock
    private VeiculoService VEICULO_SERVICE;

    private Agendamento useCase;

    @BeforeEach
    void setUp() {
        useCase = new Agendamento(CADASTRAR_OS_PORT, REGISTRO_ENTRADA_REPOSITORY, VEICULO_SERVICE);
    }

    @Test
    void deveExecutarComSucesso() {
        LocalDate dataPrevista = LocalDate.now().plusDays(1);

        OrdemDeServico ordem = new OrdemDeServico();
        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(1);

        RegistroEntrada salvo = new RegistroEntrada();
        salvo.setIdRegistroEntrada(10);

        when(CADASTRAR_OS_PORT.execute(Status.AGUARDANDO_ENTRADA, 1)).thenReturn(ordem);
        when(VEICULO_SERVICE.buscarVeiculoPeloId(1)).thenReturn(veiculo);
        when(REGISTRO_ENTRADA_REPOSITORY.save(any(RegistroEntrada.class))).thenReturn(salvo);

        RegistroEntrada retorno = useCase.execute(dataPrevista, 1);

        assertSame(salvo, retorno);
        verify(CADASTRAR_OS_PORT).execute(Status.AGUARDANDO_ENTRADA, 1);
        verify(REGISTRO_ENTRADA_REPOSITORY).save(any(RegistroEntrada.class));
    }

    @Test
    void devePropagarBadBusinessRuleException_QuandoExisteOrdemAbertaParaVeiculo() {
        when(CADASTRAR_OS_PORT.execute(Status.AGUARDANDO_ENTRADA, 1))
                .thenThrow(new BadBusinessRuleException("erro", Domains.ORDEM_DE_SERVICO));

        assertThrows(BadBusinessRuleException.class, () -> useCase.execute(LocalDate.now().plusDays(1), 1));

        verify(REGISTRO_ENTRADA_REPOSITORY, never()).save(any());
    }

}
