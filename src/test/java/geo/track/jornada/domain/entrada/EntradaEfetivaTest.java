package geo.track.jornada.domain.entrada;

import geo.track.gestao.veiculo.domain.VeiculoService;
import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.jornada.application.CadastrarEntradaUseCase;
import geo.track.jornada.application.CadastrarOrdemServicoUseCase;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.infraestructure.request.entrada.RequestEntrada;
import geo.track.jornada.infraestructure.request.entrada.RequestItemEntrada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntradaEfetivaTest {

    @Mock
    private CadastrarOrdemServicoUseCase CADASTRAR_ORDEM_PORT;

    @Mock
    private CadastrarEntradaUseCase CADASTRAR_ENTRADA_PORT;

    @Mock
    private VeiculoService VEICULO_SERVICE;

    private EntradaEfetiva useCase;

    @BeforeEach
    void setUp() {
        useCase = new EntradaEfetiva(CADASTRAR_ORDEM_PORT, CADASTRAR_ENTRADA_PORT, VEICULO_SERVICE);
    }

    @Test
    void deveExecutarComSucesso() {
        RequestEntrada request = new RequestEntrada("Carlos", "12345678901", "ok", List.of(new RequestItemEntrada("Extintor", 1)));

        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(1);

        OrdemDeServico ordem = new OrdemDeServico();

        RegistroEntrada salvo = new RegistroEntrada();
        salvo.setIdRegistroEntrada(10);

        when(VEICULO_SERVICE.buscarVeiculoPeloId(1)).thenReturn(veiculo);
        when(CADASTRAR_ORDEM_PORT.execute(Status.AGUARDANDO_ORCAMENTO, 1)).thenReturn(ordem);
        when(CADASTRAR_ENTRADA_PORT.execute(any(RegistroEntrada.class))).thenReturn(salvo);

        RegistroEntrada retorno = useCase.execute(1, request);

        assertSame(salvo, retorno);
        verify(CADASTRAR_ENTRADA_PORT).execute(any(RegistroEntrada.class));
    }

    @Test
    void devePropagarBadBusinessRuleException_QuandoExisteOrdemAbertaParaVeiculo() {
        RequestEntrada request = new RequestEntrada("Carlos", "12345678901", "ok", List.of(new RequestItemEntrada("Extintor", 1)));

        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(1);

        when(VEICULO_SERVICE.buscarVeiculoPeloId(1)).thenReturn(veiculo);
        when(CADASTRAR_ORDEM_PORT.execute(Status.AGUARDANDO_ORCAMENTO, 1))
                .thenThrow(new BadBusinessRuleException("erro", Domains.ORDEM_DE_SERVICO));

        assertThrows(BadBusinessRuleException.class, () -> useCase.execute(1, request));

        verify(CADASTRAR_ENTRADA_PORT, never()).execute(any());
    }

}
