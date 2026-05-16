package geo.track.jornada.domain.entrada;

import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.jornada.application.CadastrarEntradaUseCase;
import geo.track.jornada.domain.RegistroEntradaService;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfirmacaoTest {

    @Mock
    private OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Mock
    private CadastrarEntradaUseCase CADASTRAR_ENTRADA_PORT;

    @Mock
    private RegistroEntradaService REGISTRO_ENTRADA_SERVICE;

    private Confirmacao useCase;

    @BeforeEach
    void setUp() {
        useCase = new Confirmacao(ORDEM_SERVICO_REPOSITORY, CADASTRAR_ENTRADA_PORT, REGISTRO_ENTRADA_SERVICE);
    }

    @Test
    void deveExecutarComSucesso() {
        RequestEntrada request = new RequestEntrada("Maria", "12345678901", "ok", List.of(new RequestItemEntrada("Macaco", 1)));

        OrdemDeServico ordem = new OrdemDeServico();
        ordem.setStatus(Status.AGUARDANDO_ENTRADA);

        RegistroEntrada entrada = new RegistroEntrada();
        entrada.setFkOrdemServico(ordem);

        when(REGISTRO_ENTRADA_SERVICE.buscarEntradaPorId(1)).thenReturn(entrada);
        when(ORDEM_SERVICO_REPOSITORY.save(ordem)).thenReturn(ordem);
        when(CADASTRAR_ENTRADA_PORT.execute(any(RegistroEntrada.class))).thenAnswer(i -> i.getArgument(0));

        RegistroEntrada retorno = useCase.execute(1, request);

        assertSame(entrada, retorno);
        assertEquals(Status.AGUARDANDO_ORCAMENTO, ordem.getStatus());
        assertEquals("Maria", retorno.getResponsavel());
        verify(ORDEM_SERVICO_REPOSITORY).save(ordem);
        verify(CADASTRAR_ENTRADA_PORT).execute(entrada);
    }

    @Test
    void devePropagarDataNotFoundException_QuandoRegistroNaoExiste() {
        when(REGISTRO_ENTRADA_SERVICE.buscarEntradaPorId(1))
                .thenThrow(new DataNotFoundException("nao encontrado", Domains.REGISTRO_ENTRADA));

        RequestEntrada request = new RequestEntrada("Maria", "12345678901", "ok", List.of(new RequestItemEntrada("Macaco", 1)));

        assertThrows(DataNotFoundException.class, () -> useCase.execute(1, request));
    }

}
