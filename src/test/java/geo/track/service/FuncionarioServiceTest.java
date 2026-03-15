package geo.track.service;

import geo.track.domain.Funcionario;
import geo.track.domain.Oficina;
import geo.track.dto.funcionarios.request.RequestPostFuncionario;
import geo.track.dto.funcionarios.request.RequestPutFuncionario;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.log.LogImplementation;
import geo.track.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do FuncionarioService")
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private LogImplementation log;

    @InjectMocks
    private FuncionarioService service;

    private RequestPostFuncionario funcionarioCadastrar;
    private RequestPutFuncionario funcionarioAtualizar;
    private Funcionario funcionario;
    private Oficina oficina;

    @BeforeEach
    void setUp() {
        funcionarioCadastrar = new RequestPostFuncionario();
        funcionarioCadastrar.setNome("João da Silva Santos");
        funcionarioCadastrar.setEmail("joao.santos@example.com");
        funcionarioCadastrar.setCargo("Mecânico");
        funcionarioCadastrar.setTelefone("11987654321");
        funcionarioCadastrar.setSenha("password123");
        funcionarioCadastrar.setFkOficina(1);

        funcionarioAtualizar = new RequestPutFuncionario();
        funcionarioAtualizar.setId(1);
        funcionarioAtualizar.setNome("João da Silva Santos");
        funcionarioAtualizar.setCargo("Mecânico Sênior");

        // Arrange: Preparar Entidade
        oficina = new Oficina();
        oficina.setIdOficina(1);
        oficina.setRazaoSocial("Oficina Teste");

        funcionario = new Funcionario();
        funcionario.setIdFuncionario(1);
        funcionario.setNome("João da Silva Santos");
        funcionario.setEmail("joao.santos@example.com"); // Added email
        funcionario.setCargo("Mecânico");
        funcionario.setTelefone("11987654321");
        funcionario.setSenha("password123"); // Added password
        funcionario.setFkOficina(oficina); // Set the fkOficina
    }

    // ===== cadastrar =====
    @Test
    @DisplayName("cadastrar: Deve cadastrar novo funcionário com sucesso quando email não existe")
    void testCadastrarFuncionarioComSucesso() {
        // Arrange
        when(repository.existsByEmail(funcionarioCadastrar.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(repository.save(any(Funcionario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Funcionario resultado = service.cadastrar(funcionarioCadastrar);

        // Assert
        assertNotNull(resultado);
        assertEquals("João da Silva Santos", resultado.getNome());
        assertEquals("encodedPassword", resultado.getSenha());
        verify(repository).existsByEmail(funcionarioCadastrar.getEmail());
        verify(passwordEncoder).encode("password123");
        verify(repository).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("cadastrar: Deve lançar ConflictException quando email já existe")
    void testCadastrarFuncionarioComEmailDuplicado() {
        // Arrange
        when(repository.existsByEmail(funcionarioCadastrar.getEmail())).thenReturn(true);

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class,
            () -> service.cadastrar(funcionarioCadastrar));

        assertEquals("FUNCIONARIO", exception.getDomain());
        verify(repository).existsByEmail(funcionarioCadastrar.getEmail());
        verify(repository, never()).save(any(Funcionario.class));
    }

    // ===== buscarPorOficina =====
    @Test
    @DisplayName("buscarPorOficina: Deve retornar lista de funcionários para uma oficina")
    void testBuscarPorOficinaComResultados() {
        // Arrange
        List<Funcionario> funcionariosDaOficina = Collections.singletonList(funcionario);
        when(repository.findByFkOficina_IdOficina(oficina.getIdOficina())).thenReturn(funcionariosDaOficina);

        // Act
        List<Funcionario> resultado = service.buscarPorOficina(oficina.getIdOficina());

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(funcionario.getIdFuncionario(), resultado.get(0).getIdFuncionario());
        verify(repository).findByFkOficina_IdOficina(oficina.getIdOficina());
    }

    @Test
    @DisplayName("buscarPorOficina: Deve retornar lista vazia quando não há funcionários para a oficina")
    void testBuscarPorOficinaSemResultados() {
        // Arrange
        when(repository.findByFkOficina_IdOficina(oficina.getIdOficina())).thenReturn(Collections.emptyList());

        // Act
        List<Funcionario> resultado = service.buscarPorOficina(oficina.getIdOficina());

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findByFkOficina_IdOficina(oficina.getIdOficina());
    }

    // ===== buscarPorId =====
    @Test
    @DisplayName("buscarPorId: Deve buscar funcionário por ID com sucesso")
    void testBuscarFuncionarioPorId() {
        // Arrange
        when(repository.existsByIdFuncionario(1)).thenReturn(true);
        when(repository.getByIdFuncionario(1)).thenReturn(funcionario);

        // Act
        Funcionario resultado = service.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdFuncionario());
        assertEquals("João da Silva Santos", resultado.getNome());
        verify(repository).existsByIdFuncionario(1);
        verify(repository).getByIdFuncionario(1);
    }

    @Test
    @DisplayName("buscarPorId: Deve lançar DataNotFoundException quando ID não existe")
    void testBuscarFuncionarioPorId_NaoEncontrado() {
        // Arrange
        when(repository.existsByIdFuncionario(999)).thenReturn(false);

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.buscarPorId(999));

        assertEquals("FUNCIONARIO", exception.getDomain());
        verify(repository).existsByIdFuncionario(999);
        verify(repository, never()).getByIdFuncionario(anyInt());
    }

    // ===== atualizar =====
    @Test
    @DisplayName("atualizar: Deve atualizar funcionário com sucesso quando existe")
    void testAtualizarFuncionario() {
        // Arrange
        // Note: The service calls this.buscarPorId, which uses the repository directly.
        // We shouldn't mock service.buscarPorId when injecting mocks, instead mock the repository calls it makes.
        when(repository.existsByIdFuncionario(funcionarioAtualizar.getId())).thenReturn(true);
        when(repository.getByIdFuncionario(funcionarioAtualizar.getId())).thenReturn(funcionario);
        
        when(repository.existsById(funcionarioAtualizar.getId())).thenReturn(true);
        when(repository.save(any(Funcionario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Funcionario resultado = service.atualizar(funcionarioAtualizar);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdFuncionario());
        assertEquals("João da Silva Santos", resultado.getNome());
        assertEquals("Mecânico Sênior", resultado.getCargo());
        verify(repository).existsByIdFuncionario(funcionarioAtualizar.getId());
        verify(repository).getByIdFuncionario(funcionarioAtualizar.getId());
        verify(repository).existsById(funcionarioAtualizar.getId());
        verify(repository).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("atualizar: Deve lançar DataNotFoundException quando funcionário não existe na busca inicial")
    void testAtualizarFuncionario_NaoEncontradoBusca() {
        // Arrange
        when(repository.existsByIdFuncionario(funcionarioAtualizar.getId())).thenReturn(false);

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.atualizar(funcionarioAtualizar));

        assertEquals("FUNCIONARIO", exception.getDomain());
        verify(repository).existsByIdFuncionario(funcionarioAtualizar.getId());
        verify(repository, never()).getByIdFuncionario(anyInt());
        verify(repository, never()).existsById(any());
        verify(repository, never()).save(any(Funcionario.class));
    }
    
    @Test
    @DisplayName("atualizar: Deve lançar DataNotFoundException quando funcionário não existe na validação final")
    void testAtualizarFuncionario_NaoEncontradoValidacaoFinal() {
        // Arrange
        when(repository.existsByIdFuncionario(funcionarioAtualizar.getId())).thenReturn(true);
        when(repository.getByIdFuncionario(funcionarioAtualizar.getId())).thenReturn(funcionario);
        when(repository.existsById(funcionarioAtualizar.getId())).thenReturn(false);

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.atualizar(funcionarioAtualizar));

        assertEquals("FUNCIONARIO", exception.getDomain());
        verify(repository).existsByIdFuncionario(funcionarioAtualizar.getId());
        verify(repository).getByIdFuncionario(funcionarioAtualizar.getId());
        verify(repository).existsById(funcionarioAtualizar.getId());
        verify(repository, never()).save(any(Funcionario.class));
    }


    // ===== deletar =====
    @Test
    @DisplayName("deletar: Deve deletar funcionário com sucesso quando existe")
    void testDeletarFuncionario() {
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
    @DisplayName("deletar: Deve lançar DataNotFoundException quando funcionário não existe")
    void testDeletarFuncionario_NaoEncontrado() {
        // Arrange
        when(repository.existsById(999)).thenReturn(false);

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.deletar(999));

        assertEquals("FUNCIONARIO", exception.getDomain());
        verify(repository).existsById(999);
        verify(repository, never()).deleteById(anyInt());
    }
}
