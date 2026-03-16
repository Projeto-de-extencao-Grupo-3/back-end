package geo.track.service;

import geo.track.domain.OrdemDeServico;
import geo.track.domain.RegistroEntrada;
import geo.track.domain.Veiculo;
import geo.track.dto.os.request.PostEntradaVeiculo;
import geo.track.dto.registroEntrada.request.RequestPostEntradaAgendada;
import geo.track.dto.registroEntrada.request.RequestPutRegistroEntrada;
import geo.track.exception.DataNotFoundException;
import geo.track.log.Log;
import geo.track.log.LogImplementation;
import geo.track.repository.RegistroEntradaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do RegistroEntradaService")
class RegistroEntradaServiceTest {

    @Mock
    private RegistroEntradaRepository repository;

    @Mock
    private LogImplementation log;

    @Mock
    private VeiculoService veiculoService;
    
    @Mock
    private OrdemDeServicoService ordemDeServicoService; // Mock adicionado

    @InjectMocks
    private RegistroEntradaService service;

    private RegistroEntrada registroEntrada;
    private Veiculo veiculo;
    private RequestPostEntradaAgendada postRegistroEntrada;
    private RequestPutRegistroEntrada putRegistroEntrada;

    @BeforeEach
    void setUp() {
        // Arrange: Preparar Entidades
        veiculo = new Veiculo();
        veiculo.setIdVeiculo(1);

        registroEntrada = new RegistroEntrada();
        registroEntrada.setIdRegistroEntrada(1);
        registroEntrada.setDataEntradaPrevista(LocalDate.now().plusDays(1));
        registroEntrada.setFkVeiculo(veiculo);

        postRegistroEntrada = new RequestPostEntradaAgendada(LocalDate.now().plusDays(1), 1);
        putRegistroEntrada = new RequestPutRegistroEntrada(
                1,
                LocalDate.now(),
                "João Responsável",
                "123.456.789-00",
                1, 1, 1, 1, 1, 1, 1, 1,
                1, ""
        );
    }

    // ===== realizarAgendamentoVeiculo =====
    @Test
    @DisplayName("realizarAgendamentoVeiculo: Deve criar registro de entrada com sucesso")
    void testRealizarAgendamentoVeiculo() {
        // Arrange
        when(veiculoService.findVeiculoById(1)).thenReturn(veiculo);
        
        // Simula o salvamento e retorna o objeto com ID
        when(repository.save(any(RegistroEntrada.class))).thenAnswer(invocation -> {
            RegistroEntrada re = invocation.getArgument(0);
            if(re.getIdRegistroEntrada() == null) re.setIdRegistroEntrada(1);
            return re;
        });
        
        // Mock do serviço de OS que estava faltando
        when(ordemDeServicoService.cadastrarOrdemServico(any(PostEntradaVeiculo.class)))
                .thenReturn(new OrdemDeServico());

        // Act
        RegistroEntrada resultado = service.realizarAgendamentoVeiculo(postRegistroEntrada);

        // Assert
        assertNotNull(resultado);
        assertEquals(LocalDate.now().plusDays(1), resultado.getDataEntradaPrevista());
        verify(veiculoService).findVeiculoById(1);
        // O método realizarAgendamentoVeiculo chama save duas vezes: uma para criar e outra para atualizar com a OS
        verify(repository, times(2)).save(any(RegistroEntrada.class));
        verify(ordemDeServicoService).cadastrarOrdemServico(any(PostEntradaVeiculo.class));
    }

    // ===== listarEntradas =====
    @Test
    @DisplayName("listarEntradas: Deve retornar lista de registros quando existem")
    void testListarEntradasComResultados() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(registroEntrada));

        // Act
        List<RegistroEntrada> resultado = service.listarEntradas();

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("listarEntradas: Deve retornar lista vazia quando não existem registros")
    void testListarEntradasSemResultados() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act
        List<RegistroEntrada> resultado = service.listarEntradas();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findAll();
    }

    // ===== buscarEntradaPorId =====
    @Test
    @DisplayName("buscarEntradaPorId: Deve encontrar registro por ID com sucesso")
    void testBuscarEntradaPorId() {
        // Arrange
        when(repository.findById(1)).thenReturn(Optional.of(registroEntrada));

        // Act
        RegistroEntrada resultado = service.buscarEntradaPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdRegistroEntrada());
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("buscarEntradaPorId: Deve lançar DataNotFoundException quando ID não existe")
    void testBuscarEntradaPorId_NaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.buscarEntradaPorId(999));

        verify(repository).findById(999);
    }

    // ===== atualizarEntradaVeiculoAgendado =====
    @Test
    @DisplayName("atualizarEntradaVeiculoAgendado: Deve atualizar registro com sucesso quando existe")
    void testAtualizarEntradaVeiculoAgendado() {
        // Arrange
        RegistroEntrada registroParaAtualizar = registroEntrada;
        registroParaAtualizar.setResponsavel("João Responsável");
        registroParaAtualizar.setCpf("123.456.789-00");

        when(repository.findById(1)).thenReturn(Optional.of(registroParaAtualizar));
        when(repository.save(any(RegistroEntrada.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        RegistroEntrada resultado = service.atualizarEntradaVeiculoAgendado(putRegistroEntrada);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Responsável", resultado.getResponsavel());
        assertEquals("123.456.789-00", resultado.getCpf());
        verify(repository).findById(1);
        verify(repository).save(any(RegistroEntrada.class));
    }

    @Test
    @DisplayName("atualizarEntradaVeiculoAgendado: Deve lançar DataNotFoundException quando registro não existe")
    void testAtualizarEntradaVeiculoAgendado_NaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());
        RequestPutRegistroEntrada requestComIdInvalido = new RequestPutRegistroEntrada(
                999, LocalDate.now(), "Nome", "CPF", 1, 1, 1, 1, 1, 1, 1, 1, 1, ""
        );

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.atualizarEntradaVeiculoAgendado(requestComIdInvalido));

        verify(repository).findById(999);
        verify(repository, never()).save(any(RegistroEntrada.class));
    }

    // ===== deletarEntrada =====
    @Test
    @DisplayName("deletarEntrada: Deve deletar registro com sucesso quando existe")
    void testDeletarEntrada() {
        // Arrange
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        // Act
        assertDoesNotThrow(() -> service.deletarEntrada(1));

        // Assert
        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("deletarEntrada: Deve lançar DataNotFoundException quando registro não existe")
    void testDeletarEntrada_NaoEncontrado() {
        // Arrange
        when(repository.existsById(999)).thenReturn(false);

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.deletarEntrada(999));

        assertEquals("Registro de Entrada não encontrado ou não pertence a esta oficina", exception.getMessage());
        verify(repository).existsById(999);
        verify(repository, never()).deleteById(anyInt());
    }
}
