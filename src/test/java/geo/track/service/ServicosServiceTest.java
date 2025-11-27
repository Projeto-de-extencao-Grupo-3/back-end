package geo.track.service;

import geo.track.domain.Servicos;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.ServicosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ServicosService")
class ServicosServiceTest {

    @Mock
    private ServicosRepository repository;

    @InjectMocks
    private ServicosService service;

    private Servicos servico;

    @BeforeEach
    void setUp() {
        servico = new Servicos();
        servico.setIdServico(1);
        servico.setTituloServico("Troca de Óleo");
    }

    // --- Testes para cadastrar ---
    @Test
    @DisplayName("cadastrar: Deve cadastrar um serviço com sucesso")
    void deveCadastrarServicoComSucesso() {
        when(repository.existsByTituloServico(servico.getTituloServico())).thenReturn(false);
        when(repository.save(any(Servicos.class))).thenReturn(servico);

        Servicos resultado = service.cadastrar(servico);

        assertNotNull(resultado);
        assertEquals(servico.getTituloServico(), resultado.getTituloServico());
        verify(repository).existsByTituloServico(servico.getTituloServico());
        verify(repository).save(any(Servicos.class));
    }

    @Test
    @DisplayName("cadastrar: Deve lançar ConflictException ao tentar cadastrar título duplicado")
    void deveLancarConflictExceptionAoCadastrarTituloDuplicado() {
        when(repository.existsByTituloServico(servico.getTituloServico())).thenReturn(true);

        ConflictException exception = assertThrows(ConflictException.class, () -> {
            service.cadastrar(servico);
        });

        assertEquals("Nome já existe", exception.getMessage());
        verify(repository).existsByTituloServico(servico.getTituloServico());
        verify(repository, never()).save(any(Servicos.class));
    }

    // --- Testes para buscarPorId ---
    @Test
    @DisplayName("buscarPorId: Deve buscar um serviço pelo ID com sucesso")
    void deveBuscarServicoPorIdComSucesso() {
        when(repository.existsByIdServico(1)).thenReturn(true);
        when(repository.getByIdServico(1)).thenReturn(servico);

        Servicos resultado = service.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdServico());
        verify(repository).existsByIdServico(1);
        verify(repository).getByIdServico(1);
    }

    @Test
    @DisplayName("buscarPorId: Deve lançar DataNotFoundException ao buscar por ID inexistente")
    void deveLancarDataNotFoundExceptionAoBuscarPorIdInexistente() {
        when(repository.existsByIdServico(99)).thenReturn(false);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.buscarPorId(99);
        });

        assertEquals("Servico Não encontrado", exception.getMessage());
        verify(repository).existsByIdServico(99);
        verify(repository, never()).getByIdServico(anyInt());
    }

    // --- Testes para atualizar ---
    @Test
    @DisplayName("atualizar: Deve atualizar um serviço com sucesso")
    void deveAtualizarServicoComSucesso() {
        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(any(Servicos.class))).thenReturn(servico);

        Servicos servicoAtualizado = new Servicos();
        servicoAtualizado.setTituloServico("Troca de Óleo");

        Servicos resultado = service.atualizar(1, servicoAtualizado);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdServico());
        assertEquals("Troca de Óleo", resultado.getTituloServico());
        verify(repository).existsById(1);
        verify(repository).save(any(Servicos.class));
    }

    @Test
    @DisplayName("atualizar: Deve lançar DataNotFoundException ao tentar atualizar serviço inexistente")
    void deveLancarDataNotFoundExceptionAoAtualizarServicoInexistente() {
        when(repository.existsById(99)).thenReturn(false);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.atualizar(99, new Servicos());
        });

        assertEquals("Servico não encontrado", exception.getMessage());
        verify(repository).existsById(99);
        verify(repository, never()).save(any(Servicos.class));
    }

    // --- Testes para deletar ---
    @Test
    @DisplayName("deletar: Deve deletar um serviço com sucesso")
    void deveDeletarServicoComSucesso() {
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        assertDoesNotThrow(() -> service.deletar(1));

        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("deletar: Deve lançar DataNotFoundException ao tentar deletar serviço inexistente")
    void deveLancarDataNotFoundExceptionAoDeletarServicoInexistente() {
        when(repository.existsById(99)).thenReturn(false);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.deletar(99);
        });

        assertEquals("Servico não encontrado", exception.getMessage());
        verify(repository).existsById(99);
        verify(repository, never()).deleteById(anyInt());
    }
}