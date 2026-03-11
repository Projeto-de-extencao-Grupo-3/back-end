package geo.track.service;

import geo.track.domain.Cliente;
import geo.track.domain.Endereco;
import geo.track.domain.Oficinas;
import geo.track.dto.clientes.request.RequestPatchEmail;
import geo.track.dto.clientes.request.RequestPatchTelefone;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.dto.clientes.request.RequestPutCliente;
import geo.track.enums.cliente.TipoCliente;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.Domains;
import geo.track.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ClienteService")
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @Mock
    private OficinaService oficinaService;

    @Mock
    private EnderecoService enderecoService;

    @InjectMocks
    private ClienteService service;

    private Cliente cliente;
    private Oficinas oficina;
    private Endereco endereco;
    private RequestPostCliente requestPostCliente;
    private RequestPatchEmail requestPatchEmail;
    private RequestPatchTelefone requestPatchTelefone;
    private RequestPutCliente requestPutCliente;

    @BeforeEach
    void setUp() {
        // Arrange: Preparar Entidades
        oficina = new Oficinas();
        oficina.setIdOficina(1);
        oficina.setRazaoSocial("Oficina do Zé");

        endereco = new Endereco();
        endereco.setIdEndereco(1);
        endereco.setCep("12345678");

        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setNome("João da Silva");
        cliente.setCpfCnpj("12345678901");
        cliente.setEmail("joao@example.com");
        cliente.setTelefone("11999999999");
        cliente.setFkOficina(oficina);
        cliente.setFkEndereco(endereco);

        // DTOs usados apenas quando necessário (entrada de requisições)
        requestPostCliente = new RequestPostCliente(
                "João da Silva",
                "12345678901",
                "11999999999",
                "joao@example.com",
                TipoCliente.PESSOA_FISICA,
                1,
                1
        );

        requestPatchEmail = new RequestPatchEmail(1, "novo.email@example.com");
        requestPatchTelefone = new RequestPatchTelefone(1, "11888888888");
        requestPutCliente = new RequestPutCliente(1, "João Santos", "09876543210", "11777777777", "joao.santos@example.com");
    }

    // ===== postCliente =====
    @Test
    @DisplayName("postCliente: Deve criar cliente com sucesso quando CPF/CNPJ não existe")
    void testPostClienteComSucesso() {
        // Arrange
        when(repository.existsByCpfCnpj(requestPostCliente.getCpfCnpj())).thenReturn(false);
        when(oficinaService.findOficinasById(requestPostCliente.getFkOficina())).thenReturn(oficina);
        when(enderecoService.findEnderecoById(requestPostCliente.getFkEndereco())).thenReturn(endereco);
        when(repository.save(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente arg = invocation.getArgument(0);
            arg.setIdCliente(1);
            return arg;
        });

        // Act
        Cliente resultado = service.postCliente(requestPostCliente);

        // Assert
        assertNotNull(resultado);
        assertEquals("João da Silva", resultado.getNome());
        assertEquals("12345678901", resultado.getCpfCnpj());
        assertEquals(oficina.getIdOficina(), resultado.getFkOficina().getIdOficina());
        assertEquals(endereco.getIdEndereco(), resultado.getFkEndereco().getIdEndereco());
        verify(repository).existsByCpfCnpj(requestPostCliente.getCpfCnpj());
        verify(repository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("postCliente: Deve lançar ConflictException quando CPF/CNPJ já existe")
    void testPostClienteComCpfCnpjDuplicado() {
        // Arrange
        when(repository.existsByCpfCnpj(requestPostCliente.getCpfCnpj())).thenReturn(true);

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class,
            () -> service.postCliente(requestPostCliente));

        assertEquals("O CPF do cliente informado já existe para esta oficina", exception.getMessage());
        assertEquals(Domains.CLIENTE.name(), exception.getDomain());
        verify(repository).existsByCpfCnpj(requestPostCliente.getCpfCnpj());
        verify(repository, never()).save(any(Cliente.class));
        verify(oficinaService, never()).findOficinasById(any());
        verify(enderecoService, never()).findEnderecoById(any());
    }

    @Test
    @DisplayName("postCliente: Deve lançar DataNotFoundException quando Oficina não existe")
    void testPostClienteOficinaNaoEncontrada() {
        // Arrange
        when(repository.existsByCpfCnpj(requestPostCliente.getCpfCnpj())).thenReturn(false);
        when(oficinaService.findOficinasById(requestPostCliente.getFkOficina()))
                .thenThrow(new DataNotFoundException("Oficina não encontrada", Domains.OFICINA));

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> service.postCliente(requestPostCliente));

        assertEquals("Oficina não encontrada", exception.getMessage());
        assertEquals("OFICINA", exception.getDomain());
        verify(repository).existsByCpfCnpj(requestPostCliente.getCpfCnpj());
        verify(oficinaService).findOficinasById(requestPostCliente.getFkOficina());
        verify(enderecoService, never()).findEnderecoById(any());
        verify(repository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("postCliente: Deve lançar DataNotFoundException quando Endereco não existe")
    void testPostClienteEnderecoNaoEncontrado() {
        // Arrange
        when(repository.existsByCpfCnpj(requestPostCliente.getCpfCnpj())).thenReturn(false);
        when(oficinaService.findOficinasById(requestPostCliente.getFkOficina())).thenReturn(oficina);
        when(enderecoService.findEnderecoById(requestPostCliente.getFkEndereco()))
                .thenThrow(new DataNotFoundException("Endereço não encontrado", Domains.ENDERECO));

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> service.postCliente(requestPostCliente));

        assertEquals("Endereço não encontrado", exception.getMessage());
        assertEquals(Domains.ENDERECO.name(), exception.getDomain());
        verify(repository).existsByCpfCnpj(requestPostCliente.getCpfCnpj());
        verify(oficinaService).findOficinasById(requestPostCliente.getFkOficina());
        verify(enderecoService).findEnderecoById(requestPostCliente.getFkEndereco());
        verify(repository, never()).save(any(Cliente.class));
    }

    // ===== findClientes =====
    @Test
    @DisplayName("findClientes: Deve retornar lista de clientes quando existem")
    void testFindClientesComResultados() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(cliente));

        // Act
        List<Cliente> resultado = service.findClientes();

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(cliente.getIdCliente(), resultado.get(0).getIdCliente());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("findClientes: Deve retornar lista vazia quando não existem clientes")
    void testFindClientesSemResultados() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act
        List<Cliente> resultado = service.findClientes();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findAll();
    }

    // ===== findClienteById =====
    @Test
    @DisplayName("findClienteById: Deve encontrar cliente por ID com sucesso")
    void testFindClienteByIdComSucesso() {
        // Arrange
        when(repository.findById(1)).thenReturn(Optional.of(cliente));

        // Act
        Cliente resultado = service.findClienteById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdCliente());
        assertEquals("João da Silva", resultado.getNome());
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("findClienteById: Deve lançar DataNotFoundException quando ID não existe")
    void testFindClienteByIdNaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.findClienteById(999));

        assertEquals("O ID 999 não foi encontrado ou não pertence a esta oficina", exception.getMessage());
        assertEquals(Domains.CLIENTE.name(), exception.getDomain());
        verify(repository).findById(999);
    }

    // ===== findClienteByNome =====
    @Test
    @DisplayName("findClienteByNome: Deve encontrar clientes por nome com sucesso")
    void testFindClienteByNomeComSucesso() {
        // Arrange
        String nome = "João da Silva";
        when(repository.findByNomeContainingIgnoreCase(nome)).thenReturn(List.of(cliente));

        // Act
        List<Cliente> resultado = service.findClienteByNome(nome);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().contains(nome)));
        verify(repository).findByNomeContainingIgnoreCase(nome);
    }

    @Test
    @DisplayName("findClienteByNome: Deve lançar DataNotFoundException quando nome não existe")
    void testFindClienteByNomeNaoEncontrado() {
        // Arrange
        String nome = "Inexistente";
        when(repository.findByNomeContainingIgnoreCase(nome)).thenReturn(List.of());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.findClienteByNome(nome));

        assertEquals("O nome não foi encontrado para esta oficina", exception.getMessage());
        assertEquals(Domains.CLIENTE.name(), exception.getDomain());
        verify(repository).findByNomeContainingIgnoreCase(nome);
    }

    // ===== findClienteByCpfCnpj =====
    @Test
    @DisplayName("findClienteByCpfCnpj: Deve encontrar cliente por CPF/CNPJ com sucesso")
    void testFindClienteByCpfCnpjComSucesso() {
        // Arrange
        String cpfCnpj = "12345678901";
        when(repository.findByCpfCnpj(cpfCnpj)).thenReturn(Optional.of(cliente));

        // Act
        Cliente resultado = service.findClienteByCpfCnpj(cpfCnpj);

        // Assert
        assertNotNull(resultado);
        assertEquals(cpfCnpj, resultado.getCpfCnpj());
        verify(repository).findByCpfCnpj(cpfCnpj);
    }

    @Test
    @DisplayName("findClienteByCpfCnpj: Deve lançar DataNotFoundException quando CPF/CNPJ não existe")
    void testFindClienteByCpfCnpjNaoEncontrado() {
        // Arrange
        String cpfCnpj = "99999999999";
        when(repository.findByCpfCnpj(cpfCnpj)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.findClienteByCpfCnpj(cpfCnpj));

        assertEquals("CPF não foi encontrado para esta oficina", exception.getMessage());
        assertEquals(Domains.CLIENTE.name(), exception.getDomain());
        verify(repository).findByCpfCnpj(cpfCnpj);
    }

    // ===== patchEmailCliente =====
    @Test
    @DisplayName("patchEmailCliente: Deve atualizar email do cliente com sucesso")
    void testPatchEmailClienteComSucesso() {
        // Arrange
        Cliente clienteParaAtualizar = cliente;
        clienteParaAtualizar.setEmail("novo.email@example.com");

        when(repository.findById(requestPatchEmail.getId())).thenReturn(Optional.of(clienteParaAtualizar));
        when(repository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Cliente resultado = service.patchEmailCliente(requestPatchEmail);

        // Assert
        assertNotNull(resultado);
        assertEquals("novo.email@example.com", resultado.getEmail());
        verify(repository).findById(requestPatchEmail.getId());
        verify(repository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("patchEmailCliente: Deve lançar DataNotFoundException quando cliente não existe")
    void testPatchEmailClienteNaoEncontrado() {
        // Arrange
        when(repository.findById(requestPatchEmail.getId())).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.patchEmailCliente(requestPatchEmail));

        assertEquals("Não existe cliente com esse ID ou não pertence a esta oficina", exception.getMessage());
        verify(repository).findById(requestPatchEmail.getId());
        verify(repository, never()).save(any(Cliente.class));
    }

    // ===== patchTelefoneCliente =====
    @Test
    @DisplayName("patchTelefoneCliente: Deve atualizar telefone do cliente com sucesso")
    void testPatchTelefoneClienteComSucesso() {
        // Arrange
        Cliente clienteParaAtualizar = cliente;
        clienteParaAtualizar.setTelefone("11888888888");

        when(repository.findById(requestPatchTelefone.getId())).thenReturn(Optional.of(clienteParaAtualizar));
        when(repository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Cliente resultado = service.patchTelefoneCliente(requestPatchTelefone);

        // Assert
        assertNotNull(resultado);
        assertEquals("11888888888", resultado.getTelefone());
        verify(repository).findById(requestPatchTelefone.getId());
        verify(repository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("patchTelefoneCliente: Deve lançar DataNotFoundException quando cliente não existe")
    void testPatchTelefoneClienteNaoEncontrado() {
        // Arrange
        when(repository.findById(requestPatchTelefone.getId())).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.patchTelefoneCliente(requestPatchTelefone));

        assertEquals("Não existe cliente com esse ID ou não pertence a esta oficina", exception.getMessage());
        verify(repository).findById(requestPatchTelefone.getId());
        verify(repository, never()).save(any(Cliente.class));
    }

    // ===== putCliente =====
    @Test
    @DisplayName("putCliente: Deve atualizar todos os dados do cliente com sucesso")
    void testPutClienteComSucesso() {
        // Arrange
        Cliente clienteParaAtualizar = cliente;
        clienteParaAtualizar.setNome("João Santos");
        clienteParaAtualizar.setCpfCnpj("09876543210");
        clienteParaAtualizar.setTelefone("11777777777");
        clienteParaAtualizar.setEmail("joao.santos@example.com");

        when(repository.findById(requestPutCliente.getIdCliente())).thenReturn(Optional.of(clienteParaAtualizar));
        when(repository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Cliente resultado = service.putCliente(requestPutCliente);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Santos", resultado.getNome());
        assertEquals("09876543210", resultado.getCpfCnpj());
        assertEquals("11777777777", resultado.getTelefone());
        assertEquals("joao.santos@example.com", resultado.getEmail());
        verify(repository).findById(requestPutCliente.getIdCliente());
        verify(repository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("putCliente: Deve lançar DataNotFoundException quando cliente não existe")
    void testPutClienteNaoEncontrado() {
        // Arrange
        when(repository.findById(requestPutCliente.getIdCliente())).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.putCliente(requestPutCliente));

        assertEquals("Não existe cliente com esse ID ou não pertence a esta oficina", exception.getMessage());
        verify(repository).findById(requestPutCliente.getIdCliente());
        verify(repository, never()).save(any(Cliente.class));
    }

    // ===== deletar =====
    @Test
    @DisplayName("deletar: Deve deletar cliente com sucesso quando existe")
    void testDeletarClienteComSucesso() {
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
    @DisplayName("deletar: Deve lançar DataNotFoundException quando cliente não existe")
    void testDeletarClienteNaoEncontrado() {
        // Arrange
        when(repository.existsById(999)).thenReturn(false);

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.deletar(999));

        assertEquals("O ID 999 não foi encontrado ou não pertence a esta oficina", exception.getMessage());
        assertEquals(Domains.CLIENTE.name(), exception.getDomain());
        verify(repository).existsById(999);
        verify(repository, never()).deleteById(999);
    }
}
