package geo.track.service;

import geo.track.domain.Clientes;
import geo.track.domain.Enderecos;
import geo.track.domain.Oficinas;
import geo.track.dto.clientes.request.RequestPatchEmail;
import geo.track.dto.clientes.request.RequestPatchTelefone;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.dto.clientes.request.RequestPutCliente;
import geo.track.dto.clientes.response.ResponseGetCliente;
import geo.track.enums.cliente.TipoCliente;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.ClientesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.IllegalFormatConversionException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ClientesService")
class ClientesServiceTest {

    @Mock
    private ClientesRepository repository;

    @Mock
    private OficinaService oficinaService;

    @Mock
    private EnderecosService enderecosService;

    @InjectMocks
    private ClientesService service;

    // Entidades
    private Clientes cliente;
    private Oficinas oficina;
    private Enderecos endereco;

    // DTOs de Requisição
    private RequestPostCliente requestPostCliente;
    private RequestPatchEmail requestPatchEmail;
    private RequestPatchTelefone requestPatchTelefone;
    private RequestPutCliente requestPutCliente;


    @BeforeEach
    void setUp() {
        // Configuração de Entidades Mock
        oficina = new Oficinas();
        oficina.setIdOficina(1);
        oficina.setRazaoSocial("Oficina do Zé");

        endereco = new Enderecos();
        endereco.setIdEndereco(1);
        endereco.setCep("12345678");

        cliente = new Clientes();
        cliente.setIdCliente(1);
        cliente.setNome("João da Silva");
        cliente.setCpfCnpj("12345678901");
        cliente.setEmail("joao@example.com");
        cliente.setTelefone("11999999999");
        cliente.setFkOficina(oficina);
        cliente.setFkEndereco(endereco);

        // Configuração de DTOs de Requisição
        requestPostCliente = new RequestPostCliente(
                "João da Silva",
                "12345678901",
                "11999999999",
                "joao@example.com",
                TipoCliente.PESSOA_FISICA,
                1, // fkOficina
                1  // fkEndereco
        );

        requestPatchEmail = new RequestPatchEmail(1, "novo.email@example.com");

        requestPatchTelefone = new RequestPatchTelefone(1, "11888888888");

        requestPutCliente = new RequestPutCliente(
                1,
                "João da Silva Santos",
                "09876543210",
                "11777777777",
                "joao.santos@example.com"
        );
    }

    // --- Testes para postCliente ---
    @Test
    @DisplayName("postCliente: Deve criar um cliente com sucesso")
    void deveCriarClienteComSucesso() {
        // Configuração dos mocks (Arrange)
        when(repository.existsByCpfCnpj(requestPostCliente.getCpfCnpj())).thenReturn(false);
        when(oficinaService.findOficinasById(requestPostCliente.getFkOficina())).thenReturn(oficina);
        when(enderecosService.findEnderecoById(requestPostCliente.getFkEndereco())).thenReturn(endereco);
        when(repository.save(any(Clientes.class))).thenReturn(cliente);

        // Execução do método (Act)
        Clientes resultadoCliente = service.postCliente(requestPostCliente);

        // Verificações (Assert)
        assertNotNull(resultadoCliente);
        assertEquals(requestPostCliente.getNome(), resultadoCliente.getNome());
        assertEquals(requestPostCliente.getCpfCnpj(), resultadoCliente.getCpfCnpj());
        verify(repository).existsByCpfCnpj(requestPostCliente.getCpfCnpj());
        verify(oficinaService).findOficinasById(requestPostCliente.getFkOficina());
        verify(enderecosService).findEnderecoById(requestPostCliente.getFkEndereco());
        verify(repository).save(any(Clientes.class));
    }

    @Test
    @DisplayName("postCliente: Deve lançar ConflictException ao tentar criar cliente com CPF/CNPJ já existente")
    void deveLancarConflictExceptionAoCriarClienteComCpfCnpjExistente() {
        when(repository.existsByCpfCnpj(requestPostCliente.getCpfCnpj())).thenReturn(true);
        // Execução do método (Act)
        ConflictException resultadoCliente = assertThrows(ConflictException.class, () -> {
            service.postCliente(requestPostCliente);
        });

        // Verificações (Assert)
        assertNotNull(resultadoCliente);
        assertEquals("O CPF do cliente informado já existe", resultadoCliente.getMessage());
        assertEquals("Clientes", resultadoCliente.getDomain());
        verify(repository).existsByCpfCnpj(requestPostCliente.getCpfCnpj());
        verify(oficinaService, never()).findOficinasById(requestPostCliente.getFkOficina());
        verify(enderecosService, never()).findEnderecoById(requestPostCliente.getFkEndereco());
        verify(repository, never()).save(any(Clientes.class));
    }

    // --- Testes para findClientes ---
    @Test
    @DisplayName("findClientes: Deve retornar uma lista de clientes")
    void deveRetornarListaDeClientes() {
        when(repository.findAll()).thenReturn(List.of(cliente, cliente));

        // Execução do método (Act)
        List<Clientes> resultadoClientes = service.findClientes();

        // Verificações (Assert)
        assertFalse(resultadoClientes.isEmpty());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("findClientes: Deve retornar uma lista vazia quando não houver clientes")
    void deveRetornarListaVaziaQuandoNaoHouverClientes() {
        when(repository.findAll()).thenReturn(List.of());

        // Execução do método (Act)
        List<Clientes> resultadoClientes = service.findClientes();

        // Verificações (Assert)
        assertTrue(resultadoClientes.isEmpty());
        verify(repository).findAll();
    }

    // --- Testes para findClienteById ---
    @Test
    @DisplayName("findClienteById: Deve encontrar um cliente pelo ID com sucesso")
    void deveEncontrarClientePorIdComSucesso() {
        Integer idDesejado = 1;

        when(repository.findById(idDesejado)).thenReturn(Optional.of(cliente));

        // Execução do método (Act)
        Clientes resultadoCliente = service.findClienteById(idDesejado);

        // Verificações (Assert)
        assertNotNull(resultadoCliente);
        assertEquals(idDesejado, resultadoCliente.getIdCliente());
        verify(repository).findById(idDesejado);
    }

    @Test
    @DisplayName("findClienteById: Deve lançar DataNotFoundException ao buscar por um ID inexistente")
    void deveLancarDataNotFoundExceptionAoBuscarClientePorIdInexistente() {
        Integer idDesejado = 2;

        when(repository.findById(idDesejado)).thenReturn(Optional.empty());

        // Execução do método (Act)
        DataNotFoundException resultadoCliente = assertThrows(DataNotFoundException.class, () -> {
            service.findClienteById(idDesejado);
        });

        // Verificações (Assert)
        assertNotNull(resultadoCliente);
        assertEquals("O ID %d não foi encontrado".formatted(idDesejado), resultadoCliente.getMessage());
        assertEquals("Clientes", resultadoCliente.getDomain());
        verify(repository).findById(idDesejado);
    }

    // --- Testes para findClienteByNome ---
    @Test
    @DisplayName("findClienteByNome: Deve encontrar clientes pelo nome com sucesso")
    void deveEncontrarClientePorNomeComSucesso() {
        String nomeDesejado = "João da Silva";

        when(repository.findByNomeContainingIgnoreCase(nomeDesejado)).thenReturn(List.of(cliente));

        // Execução do método (Act)
        List<Clientes> resultadoCliente = service.findClienteByNome(nomeDesejado);

        // Verificações (Assert)
        assertNotNull(resultadoCliente);
        assertTrue(resultadoCliente.stream().anyMatch(c -> c.getNome().contains(nomeDesejado)));
        verify(repository).findByNomeContainingIgnoreCase(nomeDesejado);
    }

    @Test
    @DisplayName("findClienteByNome: Deve lançar DataNotFoundException ao buscar por um nome inexistente")
    void deveLancarDataNotFoundExceptionAoBuscarClientePorNomeInexistente() {
        String nomeDesejado = "João da Silva";

        when(repository.findByNomeContainingIgnoreCase(nomeDesejado)).thenReturn(List.of());

        // Execução do método (Act)
        DataNotFoundException resultadoCliente = assertThrows(DataNotFoundException.class, () -> {
            service.findClienteByNome(nomeDesejado);
        });

        // Verificações (Assert)
        assertNotNull(resultadoCliente);
        assertEquals("O nome %s não foi encontrado".formatted(nomeDesejado), resultadoCliente.getMessage());
        assertEquals("Clientes", resultadoCliente.getDomain());
        verify(repository).findByNomeContainingIgnoreCase(nomeDesejado);
    }

    // --- Testes para findClienteByCpfCnpj ---
    @Test
    @DisplayName("findClienteByCpfCnpj: Deve encontrar um cliente pelo CPF/CNPJ com sucesso")
    void deveEncontrarClientePorCpfCnpjComSucesso() {
        String cpfCnpjDesejado = "12345678901";
        Integer idDesejado = 1;

        when(repository.findByCpfCnpj(cpfCnpjDesejado)).thenReturn(Optional.of(cliente));

        Clientes resultadoCliente = service.findClienteByCpfCnpj(cpfCnpjDesejado);

        assertEquals(idDesejado, resultadoCliente.getIdCliente());
        verify(repository).findByCpfCnpj(cpfCnpjDesejado);
    }

    @Test
    @DisplayName("findClienteByCpfCnpj: Deve lançar DataNotFoundException ao buscar por um CPF/CNPJ inexistente")
    void deveLancarDataNotFoundExceptionAoBuscarClientePorCpfCnpjInexistente() {
        String cpfCnpjDesejado = "12345678901";

        when(repository.findByCpfCnpj(cpfCnpjDesejado)).thenReturn(Optional.empty());

        // Execução do método (Act)
        DataNotFoundException resultadoCliente = assertThrows(DataNotFoundException.class, () -> {
            service.findClienteByCpfCnpj(cpfCnpjDesejado);
        });

        // Verificações (Assert)
        assertNotNull(resultadoCliente);
        assertEquals("CPF %s não foi encontrado".formatted(cpfCnpjDesejado), resultadoCliente.getMessage());
        assertEquals("Clientes", resultadoCliente.getDomain());
        verify(repository).findByCpfCnpj(cpfCnpjDesejado);
    }

    // --- Testes para patchEmailCliente ---
    @Test
    @DisplayName("patchEmailCliente: Deve atualizar o email do cliente com sucesso")
    void deveAtualizarEmailClienteComSucesso() {
        when(repository.findById(requestPatchEmail.getId())).thenReturn(Optional.of(cliente));
        when(repository.save(any(Clientes.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Clientes clienteAtualizado = service.patchEmailCliente(requestPatchEmail);

        assertNotNull(clienteAtualizado);
        assertEquals(requestPatchEmail.getEmail(), clienteAtualizado.getEmail());
        verify(repository).findById(requestPatchEmail.getId());
        verify(repository).save(any(Clientes.class));
    }

    @Test
    @DisplayName("patchEmailCliente: Deve lançar DataNotFoundException ao tentar atualizar email de cliente inexistente")
    void deveLancarDataNotFoundExceptionAoAtualizarEmailDeClienteInexistente() {
        when(repository.findById(requestPatchEmail.getId())).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.patchEmailCliente(requestPatchEmail);
        });

        assertEquals("Não existe cliente com esse ID", exception.getMessage());
        verify(repository).findById(requestPatchEmail.getId());
        verify(repository, never()).save(any(Clientes.class));
    }

    // --- Testes para patchTelefoneCliente ---
    @Test
    @DisplayName("patchTelefoneCliente: Deve atualizar o telefone do cliente com sucesso")
    void deveAtualizarTelefoneClienteComSucesso() {
        when(repository.findById(requestPatchTelefone.getId())).thenReturn(Optional.of(cliente));
        when(repository.save(any(Clientes.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Clientes clienteAtualizado = service.patchTelefoneCliente(requestPatchTelefone);

        assertNotNull(clienteAtualizado);
        assertEquals(requestPatchTelefone.getTelefone(), clienteAtualizado.getTelefone());
        verify(repository).findById(requestPatchTelefone.getId());
        verify(repository).save(any(Clientes.class));
    }

    @Test
    @DisplayName("patchTelefoneCliente: Deve lançar DataNotFoundException ao tentar atualizar telefone de cliente inexistente")
    void deveLancarDataNotFoundExceptionAoAtualizarTelefoneDeClienteInexistente() {
        when(repository.findById(requestPatchTelefone.getId())).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.patchTelefoneCliente(requestPatchTelefone);
        });

        assertEquals("Não existe cliente com esse ID", exception.getMessage());
        verify(repository).findById(requestPatchTelefone.getId());
        verify(repository, never()).save(any(Clientes.class));
    }

    // --- Testes para putCliente ---
    @Test
    @DisplayName("putCliente: Deve atualizar os dados de um cliente com sucesso")
    void deveAtualizarDadosClienteComSucesso() {
        when(repository.findById(requestPutCliente.getIdCliente())).thenReturn(Optional.of(cliente));
        when(repository.save(any(Clientes.class))).thenReturn(cliente);
        Clientes clienteAtualizado = service.putCliente(requestPutCliente);

        assertNotNull(clienteAtualizado);
        assertEquals(requestPutCliente.getNome(), clienteAtualizado.getNome());
        assertEquals(requestPutCliente.getCpfCnpj(), clienteAtualizado.getCpfCnpj());
        assertEquals(requestPutCliente.getTelefone(), clienteAtualizado.getTelefone());
        assertEquals(requestPutCliente.getEmail(), clienteAtualizado.getEmail());
        verify(repository).findById(requestPutCliente.getIdCliente());
        verify(repository).save(any(Clientes.class));
    }

    @Test
    @DisplayName("putCliente: Deve lançar DataNotFoundException ao tentar atualizar um cliente inexistente")
    void deveLancarDataNotFoundExceptionAoAtualizarDadosDeClienteInexistente() {
        when(repository.findById(requestPutCliente.getIdCliente())).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.putCliente(requestPutCliente);
        });

        assertEquals("Não existe cliente com esse ID", exception.getMessage());
        verify(repository).findById(requestPutCliente.getIdCliente());
        verify(repository, never()).save(any(Clientes.class));
    }

    // --- Testes para deletar ---
    @Test
    @DisplayName("deletar: Deve deletar um cliente com sucesso")
    void deveDeletarClienteComSucesso() {
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        assertDoesNotThrow(() -> service.deletar(1));

        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("deletar: Deve lançar exceção ao tentar deletar um cliente inexistente")
    void deveLancarExcecaoAoTentarDeletarClienteInexistente() {
        Integer idDesejado = 1;

        when(repository.existsById(idDesejado)).thenReturn(false);

        DataNotFoundException resultadoCliente = assertThrows(DataNotFoundException.class, () -> {
            service.deletar(idDesejado);
        });

        assertEquals("O ID %d não foi encontrado".formatted(idDesejado), resultadoCliente.getMessage());
        assertEquals("Clientes", resultadoCliente.getDomain());
        verify(repository, never()).deleteById(idDesejado);
    }
}