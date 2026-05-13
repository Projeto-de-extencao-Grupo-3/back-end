package geo.track.jornada.domain.entrada;

import geo.track.gestao.veiculo.application.CadastrarVeiculoUseCase;
import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.veiculo.infraestructure.request.RequestPostVeiculo;
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
class EntradaEfetivaSemCadastroTest {

    @Mock
    private CadastrarOrdemServicoUseCase CADASTRAR_ORDEM_PORT;

    @Mock
    private CadastrarEntradaUseCase CADASTRAR_ENTRADA_PORT;

    @Mock
    private CadastrarVeiculoUseCase CADASTRAR_VEICULO_PORT;

    private EntradaEfetivaSemCadastro useCase;

    @BeforeEach
    void setUp() {
        useCase = new EntradaEfetivaSemCadastro(CADASTRAR_ORDEM_PORT, CADASTRAR_ENTRADA_PORT, CADASTRAR_VEICULO_PORT);
    }

    @Test
    void deveExecutarComSucesso() {
        RequestPostVeiculo requestVeiculo = new RequestPostVeiculo();
        requestVeiculo.setPlaca("BRA2E19");
        requestVeiculo.setMarca("VW");
        requestVeiculo.setModelo("Nivus");
        requestVeiculo.setPrefixo("123");
        requestVeiculo.setAnoModelo(2023);
        requestVeiculo.setIdCliente(1);

        RequestEntrada requestEntrada = new RequestEntrada("Carlos", "12345678901", "ok", List.of(new RequestItemEntrada("Extintor", 1)));

        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(1);

        OrdemDeServico ordem = new OrdemDeServico();

        RegistroEntrada salvo = new RegistroEntrada();
        salvo.setIdRegistroEntrada(10);

        when(CADASTRAR_VEICULO_PORT.execute(requestVeiculo)).thenReturn(veiculo);
        when(CADASTRAR_ORDEM_PORT.execute(Status.AGUARDANDO_ORCAMENTO, 1)).thenReturn(ordem);
        when(CADASTRAR_ENTRADA_PORT.execute(any(RegistroEntrada.class))).thenReturn(salvo);

        RegistroEntrada retorno = useCase.execute(requestVeiculo, requestEntrada);

        assertSame(salvo, retorno);
        verify(CADASTRAR_ENTRADA_PORT).execute(any(RegistroEntrada.class));
    }

    @Test
    void devePropagarBadBusinessRuleException_QuandoExisteOrdemAbertaParaVeiculo() {
        RequestPostVeiculo requestVeiculo = new RequestPostVeiculo();
        RequestEntrada requestEntrada = new RequestEntrada("Carlos", "12345678901", "ok", List.of(new RequestItemEntrada("Extintor", 1)));

        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(1);

        when(CADASTRAR_VEICULO_PORT.execute(requestVeiculo)).thenReturn(veiculo);
        when(CADASTRAR_ORDEM_PORT.execute(Status.AGUARDANDO_ORCAMENTO, 1))
                .thenThrow(new BadBusinessRuleException("erro", Domains.ORDEM_DE_SERVICO));

        assertThrows(BadBusinessRuleException.class, () -> useCase.execute(requestVeiculo, requestEntrada));

        verify(CADASTRAR_ENTRADA_PORT, never()).execute(any());
    }

}
