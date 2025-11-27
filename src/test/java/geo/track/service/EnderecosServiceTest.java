package geo.track.service;

import geo.track.domain.Enderecos;
import geo.track.dto.enderecos.request.RequestPatchComplemento;
import geo.track.dto.enderecos.request.RequestPatchNumero;
import geo.track.dto.enderecos.request.RequestPostEndereco;
import geo.track.dto.enderecos.request.RequestPutEndereco;
import geo.track.dto.viacep.response.ResponseViacep;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.NotAcepptableException;
import geo.track.repository.EnderecosRepository;
import geo.track.util.ViacepConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do EnderecosService")
class EnderecosServiceTest {

    @Mock
    private EnderecosRepository repository;

    @Mock
    private ViacepConnection viacepConnection;

    @InjectMocks
    private EnderecosService service;

    // Entidade
    private Enderecos endereco;

    // DTOs de Requisição
    private RequestPostEndereco requestPostEndereco;
    private RequestPatchComplemento requestPatchComplemento;
    private RequestPatchNumero requestPatchNumero;
    private RequestPutEndereco requestPutEndereco;

    @BeforeEach
    void setUp() {
        // Configuração da Entidade Mock
        endereco = new Enderecos();
        endereco.setIdEndereco(1);
        endereco.setCep("01001000");
        endereco.setLogradouro("Praça da Sé");
        endereco.setNumero(100);
        endereco.setComplemento("Lado ímpar");
        endereco.setBairro("Sé");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");

        // Configuração dos DTOs de Requisição
        requestPostEndereco = new RequestPostEndereco(
                "01001000",
                "Praça da Sé",
                100,
                "Lado ímpar",
                "Sé",
                "São Paulo",
                "SP"
        );

        requestPatchComplemento = new RequestPatchComplemento(1, "Novo Complemento");
        requestPatchNumero = new RequestPatchNumero(1, 200);

        requestPutEndereco = new RequestPutEndereco(
                1,
                "04538133",
                "Av. Brigadeiro Faria Lima",
                1571,
                "Andar 10",
                "Jardim Paulistano",
                "São Paulo",
                "SP"
        );
    }

    // --- Testes para findEnderecoById ---
    @Test
    @DisplayName("findEnderecoById: Deve encontrar um endereço pelo ID com sucesso")
    void deveEncontrarEnderecoPorIdComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(endereco));

        Enderecos resultado = service.findEnderecoById(1);

        assertNotNull(resultado);
        assertEquals(endereco.getIdEndereco(), resultado.getIdEndereco());
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("findEnderecoById: Deve lançar DataNotFoundException ao buscar por um ID inexistente")
    void deveLancarDataNotFoundExceptionAoBuscarEnderecoPorIdInexistente() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.findEnderecoById(99);
        });

        assertEquals("ID 99 não foi encontrado", exception.getMessage());
        verify(repository).findById(99);
    }

    // --- Testes para findEnderecoByVIACEP ---
    @Test
    @DisplayName("findEnderecoByVIACEP: Deve retornar dados do CEP com sucesso")
    void deveRetornarDadosDoCepComSucesso() {
        String cep = "01001000";
        ResponseViacep responseViacep = new ResponseViacep(cep, "Praça da Sé", "Sé", "São Paulo", "SP");
        when(viacepConnection.consultarCEP(cep)).thenReturn(responseViacep);

        ResponseViacep resultado = service.findEnderecoByVIACEP(cep);

        assertNotNull(resultado);
        assertEquals(cep, resultado.getCep());
        verify(viacepConnection).consultarCEP(cep);
    }

    @Test
    @DisplayName("findEnderecoByVIACEP: Deve lançar NotAcepptableException para CEP com formato inválido")
    void deveLancarExcecaoParaCepComFormatoInvalido() {
        String cepInvalido = "12345";

        NotAcepptableException exception = assertThrows(NotAcepptableException.class, () -> {
            service.findEnderecoByVIACEP(cepInvalido);
        });

        assertEquals("Envie um CEP que possua 8 caracteres", exception.getMessage());
        verify(viacepConnection, never()).consultarCEP(anyString());
    }

    // --- Testes para postEndereco ---
    @Test
    @DisplayName("postEndereco: Deve criar um novo endereço com sucesso")
    void deveCriarEnderecoComSucesso() {
        when(repository.save(any(Enderecos.class))).thenReturn(endereco);

        Enderecos resultado = service.postEndereco(requestPostEndereco);

        assertNotNull(resultado);
        assertEquals(requestPostEndereco.getCep(), resultado.getCep());
        verify(repository).save(any(Enderecos.class));
    }

    @Test
    @DisplayName("postEndereco: Deve lançar NotAcepptableException ao tentar criar endereço com CEP inválido")
    void deveLancarExcecaoAoCriarEnderecoComCepInvalido() {
        requestPostEndereco.setCep("123");

        NotAcepptableException exception = assertThrows(NotAcepptableException.class, () -> {
            service.postEndereco(requestPostEndereco);
        });

        assertEquals("Envie um CEP que possua 8 caracteres", exception.getMessage());
        verify(repository, never()).save(any(Enderecos.class));
    }

    // --- Testes para patchComplementoEndereco ---
    @Test
    @DisplayName("patchComplementoEndereco: Deve atualizar o complemento com sucesso")
    void deveAtualizarComplementoComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(endereco));
        when(repository.save(any(Enderecos.class))).thenAnswer(inv -> inv.getArgument(0));

        Enderecos resultado = service.patchComplementoEndereco(requestPatchComplemento);

        assertNotNull(resultado);
        assertEquals(requestPatchComplemento.getComplemento(), resultado.getComplemento());
        verify(repository).findById(1);
        verify(repository).save(any(Enderecos.class));
    }

    @Test
    @DisplayName("patchComplementoEndereco: Deve lançar DataNotFoundException ao tentar atualizar complemento de ID inexistente")
    void deveLancarExcecaoAoAtualizarComplementoDeIdInexistente() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.patchComplementoEndereco(new RequestPatchComplemento(99, ""));
        });

        assertEquals("Endereço com o ID 99 não foi encontrado", exception.getMessage());
        verify(repository).findById(99);
        verify(repository, never()).save(any(Enderecos.class));
    }

    // --- Testes para patchNumeroEndereco ---
    @Test
    @DisplayName("patchNumeroEndereco: Deve atualizar o número com sucesso")
    void deveAtualizarNumeroComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(endereco));
        when(repository.save(any(Enderecos.class))).thenAnswer(inv -> inv.getArgument(0));

        Enderecos resultado = service.patchNumeroEndereco(requestPatchNumero);

        assertNotNull(resultado);
        assertEquals(requestPatchNumero.getNumero(), resultado.getNumero());
        verify(repository).findById(1);
        verify(repository).save(any(Enderecos.class));
    }

    @Test
    @DisplayName("patchNumeroEndereco: Deve lançar DataNotFoundException ao tentar atualizar número de ID inexistente")
    void deveLancarExcecaoAoAtualizarNumeroDeIdInexistente() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.patchNumeroEndereco(new RequestPatchNumero(99, 0));
        });

        assertEquals("Endereço com o ID 99 não foi encontrado", exception.getMessage());
        verify(repository).findById(99);
        verify(repository, never()).save(any(Enderecos.class));
    }

    // --- Testes para putEndereco ---
    @Test
    @DisplayName("putEndereco: Deve atualizar o endereço completo com sucesso")
    void deveAtualizarEnderecoCompletoComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(endereco));
        when(repository.save(any(Enderecos.class))).thenAnswer(inv -> inv.getArgument(0));

        Enderecos resultado = service.putEndereco(requestPutEndereco);

        assertNotNull(resultado);
        assertEquals(requestPutEndereco.getCep(), resultado.getCep());
        assertEquals(requestPutEndereco.getLogradouro(), resultado.getLogradouro());
        assertEquals(requestPutEndereco.getNumero(), resultado.getNumero());
        verify(repository).findById(1);
        verify(repository).save(any(Enderecos.class));
    }

    @Test
    @DisplayName("putEndereco: Deve lançar NotAcepptableException ao tentar atualizar com CEP inválido")
    void deveLancarExcecaoAoAtualizarComCepInvalido() {
        requestPutEndereco.setCep("123");

        NotAcepptableException exception = assertThrows(NotAcepptableException.class, () -> {
            service.putEndereco(requestPutEndereco);
        });

        assertEquals("Envie um CEP que possua 8 caracteres", exception.getMessage());
        verify(repository, never()).findById(anyInt());
        verify(repository, never()).save(any(Enderecos.class));
    }

    @Test
    @DisplayName("putEndereco: Deve lançar DataNotFoundException ao tentar atualizar endereço de ID inexistente")
    void deveLancarExcecaoAoAtualizarEnderecoDeIdInexistente() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        requestPutEndereco.setId(99);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.putEndereco(requestPutEndereco);
        });

        assertEquals("Endereço com o ID 99 não foi encontrado", exception.getMessage());
        verify(repository).findById(99);
        verify(repository, never()).save(any(Enderecos.class));
    }
}