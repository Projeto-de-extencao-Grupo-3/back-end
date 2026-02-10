package geo.track.service;

import geo.track.domain.Funcionario;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do FuncionariosService")
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository repository;

    @InjectMocks
    private FuncionarioService service;

    private Funcionario funcionario;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario();
        funcionario.setIdFuncionario(1);
        funcionario.setNome("João da Silva Santos");
        funcionario.setCargo("Mecânico");
        funcionario.setSenha("123456");
        funcionario.setEmail("joao@gameplay.com");
        funcionario.setTelefone("11987654321");
    }

    // --- Testes para cadastrar ---
    @Test
    @DisplayName("cadastrar: Deve cadastrar um funcionário com sucesso")
    void deveCadastrarFuncionarioComSucesso() {
        when(repository.existsByEmail(funcionario.getEmail())).thenReturn(false);
        when(repository.save(any(Funcionario.class))).thenReturn(funcionario);

        Funcionario resultado = service.cadastrar(funcionario);

        assertNotNull(resultado);
        assertEquals(funcionario.getNome(), resultado.getNome());
        verify(repository).existsByEmail(funcionario.getEmail());
        verify(passwordEncoder).encode("123456");
        verify(repository).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("cadastrar: Deve lançar ConflictException ao tentar cadastrar nome duplicado")
    void deveLancarConflictExceptionAoCadastrarNomeDuplicado() {
        when(repository.existsByEmail(funcionario.getEmail())).thenReturn(true);

        ConflictException exception = assertThrows(ConflictException.class, () -> {
            service.cadastrar(funcionario);
        });

        assertEquals("Funcionário já existente com este email!", exception.getMessage());
        verify(repository).existsByEmail(funcionario.getEmail());
        verify(repository, never()).save(any(Funcionario.class));
    }

    // --- Testes para buscarPorId ---
    @Test
    @DisplayName("buscarPorId: Deve buscar um funcionário pelo ID com sucesso")
    void deveBuscarFuncionarioPorIdComSucesso() {
        when(repository.existsByIdFuncionario(1)).thenReturn(true);
        when(repository.getByIdFuncionario(1)).thenReturn(funcionario);

        Funcionario resultado = service.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdFuncionario());
        verify(repository).existsByIdFuncionario(1);
        verify(repository).getByIdFuncionario(1);
    }

    @Test
    @DisplayName("buscarPorId: Deve lançar DataNotFoundException ao buscar por ID inexistente")
    void deveLancarDataNotFoundExceptionAoBuscarPorIdInexistente() {
        when(repository.existsByIdFuncionario(99)).thenReturn(false);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.buscarPorId(99);
        });

        assertEquals("Funcionario não encontrado", exception.getMessage());
        verify(repository).existsByIdFuncionario(99);
        verify(repository, never()).getByIdFuncionario(anyInt());
    }

    // --- Testes para atualizar ---
    @Test
    @DisplayName("atualizar: Deve atualizar um funcionário com sucesso")
    void deveAtualizarFuncionarioComSucesso() {
        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(any(Funcionario.class))).thenReturn(funcionario);

        Funcionario funcionarioAtualizado = new Funcionario();
        funcionarioAtualizado.setNome("João da Silva Santos");

        Funcionario resultado = service.atualizar(1, funcionarioAtualizado);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdFuncionario());
        assertEquals("João da Silva Santos", resultado.getNome());
        verify(repository).existsById(1);
        verify(repository).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("atualizar: Deve lançar DataNotFoundException ao tentar atualizar funcionário inexistente")
    void deveLancarDataNotFoundExceptionAoAtualizarFuncionarioInexistente() {
        when(repository.existsById(99)).thenReturn(false);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.atualizar(99, new Funcionario());
        });

        assertEquals("Funcionario não encontrado", exception.getMessage());
        verify(repository).existsById(99);
        verify(repository, never()).save(any(Funcionario.class));
    }

    // --- Testes para deletar ---
    @Test
    @DisplayName("deletar: Deve deletar um funcionário com sucesso")
    void deveDeletarFuncionarioComSucesso() {
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        assertDoesNotThrow(() -> service.deletar(1));

        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("deletar: Deve lançar DataNotFoundException ao tentar deletar funcionário inexistente")
    void deveLancarDataNotFoundExceptionAoDeletarFuncionarioInexistente() {
        when(repository.existsById(99)).thenReturn(false);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.deletar(99);
        });

        assertEquals("Funcionario não encontrado", exception.getMessage());
        verify(repository).existsById(99);
        verify(repository, never()).deleteById(anyInt());
    }
}