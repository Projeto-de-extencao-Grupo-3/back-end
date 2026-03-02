package geo.track.service;

import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ServicoService")
class ServicoServiceTest {

    @Mock
    private ServicoRepository repository;

    @InjectMocks
    private ServicoService service;

    private Servico servico;

    @BeforeEach
    void setUp() {
        // Arrange: Preparar Entidade
        servico = new Servico();
        servico.setIdServico(1);
        servico.setTituloServico("Troca de Óleo");
    }

    // ===== cadastrar =====
    @Test
    @DisplayName("cadastrar: Deve cadastrar novo serviço com sucesso quando título não existe")
    void testCadastrarServicoComSucesso() {
        // Arrange
        when(repository.existsByTituloServico(servico.getTituloServico())).thenReturn(false);
        when(repository.save(any(Servico.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Servico resultado = service.cadastrar(servico);

        // Assert
        assertNotNull(resultado);
        assertEquals("Troca de Óleo", resultado.getTituloServico());
        verify(repository).existsByTituloServico(servico.getTituloServico());
        verify(repository).save(any(Servico.class));
    }

    @Test
    @DisplayName("cadastrar: Deve lançar ConflictException quando título já existe")
    void testCadastrarServicoComTituloDuplicado() {
        // Arrange
        when(repository.existsByTituloServico(servico.getTituloServico())).thenReturn(true);

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class,
            () -> service.cadastrar(servico));

        assertEquals("Nome já existe", exception.getMessage());
        verify(repository).existsByTituloServico(servico.getTituloServico());
        verify(repository, never()).save(any(Servico.class));
    }

    // ===== buscarPorId =====
    @Test
    @DisplayName("buscarPorId: Deve buscar serviço por ID com sucesso")
    void testBuscarServicoPorId() {
        // Arrange
        when(repository.existsByIdServico(1)).thenReturn(true);
        when(repository.getByIdServico(1)).thenReturn(servico);

        // Act
        Servico resultado = service.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdServico());
        assertEquals("Troca de Óleo", resultado.getTituloServico());
        verify(repository).existsByIdServico(1);
        verify(repository).getByIdServico(1);
    }

    @Test
    @DisplayName("buscarPorId: Deve lançar DataNotFoundException quando ID não existe")
    void testBuscarServicoPorId_NaoEncontrado() {
        // Arrange
        when(repository.existsByIdServico(999)).thenReturn(false);

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.buscarPorId(999));

        assertEquals("Servico Não encontrado", exception.getMessage());
        verify(repository).existsByIdServico(999);
        verify(repository, never()).getByIdServico(anyInt());
    }

    // ===== atualizar =====
    @Test
    @DisplayName("atualizar: Deve atualizar serviço com sucesso quando existe")
    void testAtualizarServico() {
        // Arrange
        Servico servicoAtualizado = servico;
        servicoAtualizado.setTituloServico("Troca de Óleo Sintético");

        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(any(Servico.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Servico resultado = service.atualizar(1, servicoAtualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdServico());
        assertEquals("Troca de Óleo Sintético", resultado.getTituloServico());
        verify(repository).existsById(1);
        verify(repository).save(any(Servico.class));
    }

    @Test
    @DisplayName("atualizar: Deve lançar DataNotFoundException quando serviço não existe")
    void testAtualizarServico_NaoEncontrado() {
        // Arrange
        when(repository.existsById(999)).thenReturn(false);

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.atualizar(999, servico));

        assertEquals("Servico não encontrado", exception.getMessage());
        verify(repository).existsById(999);
        verify(repository, never()).save(any(Servico.class));
    }

    // ===== deletar =====
    @Test
    @DisplayName("deletar: Deve deletar serviço com sucesso quando existe")
    void testDeletarServico() {
        // Arrange
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        // Act
        assertDoesNotThrow(() -> service.deletar(1));

        // Assert
        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("deletar: Deve lançar DataNotFoundException quando serviço não existe")
    void testDeletarServico_NaoEncontrado() {
        // Arrange
        when(repository.existsById(999)).thenReturn(false);

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.deletar(999));

        assertEquals("Servico não encontrado", exception.getMessage());
        verify(repository).existsById(999);
        verify(repository, never()).deleteById(anyInt());
    }
}
