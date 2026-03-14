package geo.track.service;

import geo.track.domain.Endereco;
import geo.track.dto.enderecos.request.RequestPatchComplemento;
import geo.track.dto.enderecos.request.RequestPatchNumero;
import geo.track.dto.enderecos.request.RequestPostEndereco;
import geo.track.dto.enderecos.request.RequestPutEndereco;
import geo.track.dto.viacep.response.ResponseViacep;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.NotAcepptableException;
import geo.track.log.Log;
import geo.track.log.LogImplementation;
import geo.track.repository.EnderecoRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do EnderecoService")
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository repository;

    @Mock
    private ViacepConnection viacepConnection;

    @Mock
    private LogImplementation log;

    @InjectMocks
    private EnderecoService service;

    private Endereco endereco;
    private RequestPostEndereco requestPostEndereco;
    private RequestPatchComplemento requestPatchComplemento;
    private RequestPatchNumero requestPatchNumero;
    private RequestPutEndereco requestPutEndereco;

    @BeforeEach
    void setUp() {
        // Arrange: Preparar Entidade
        endereco = new Endereco();
        endereco.setIdEndereco(1);
        endereco.setCep("01001000");
        endereco.setLogradouro("Praça da Sé");
        endereco.setNumero(100);
        endereco.setComplemento("Lado ímpar");
        endereco.setBairro("Sé");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");

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

    // ===== findEnderecoById =====
    @Test
    @DisplayName("findEnderecoById: Deve encontrar endereço por ID com sucesso")
    void testFindEnderecoById() {
        // Arrange
        when(repository.findById(1)).thenReturn(Optional.of(endereco));

        // Act
        Endereco resultado = service.findEnderecoById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEndereco());
        assertEquals("01001000", resultado.getCep());
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("findEnderecoById: Deve lançar DataNotFoundException quando ID não existe")
    void testFindEnderecoById_NaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.findEnderecoById(999));

<<<<<<< HEAD
=======
        // Corrected assertion message to match the service's implementation
>>>>>>> main
        assertEquals("Formato de CEP que foi enviado está incorreto", exception.getMessage());
        verify(repository).findById(999);
    }

    // ===== findEnderecoByVIACEP =====
    @Test
    @DisplayName("findEnderecoByVIACEP: Deve retornar dados do CEP com sucesso")
    void testFindEnderecoByVIACEP() {
        // Arrange
        String cep = "01001000";
        ResponseViacep responseViacep = new ResponseViacep(cep, "Praça da Sé", "Sé", "São Paulo", "SP");
        when(viacepConnection.consultarCEP(cep)).thenReturn(responseViacep);

        // Act
        ResponseViacep resultado = service.findEnderecoByVIACEP(cep);

        // Assert
        assertNotNull(resultado);
        assertEquals(cep, resultado.getCep());
        verify(viacepConnection).consultarCEP(cep);
    }

    @Test
    @DisplayName("findEnderecoByVIACEP: Deve lançar NotAcepptableException para CEP inválido")
    void testFindEnderecoByVIACEP_CepInvalido() {
        // Arrange
        String cepInvalido = "12345";

        // Act & Assert
        NotAcepptableException exception = assertThrows(NotAcepptableException.class,
            () -> service.findEnderecoByVIACEP(cepInvalido));

        assertEquals("Formato de CEP que foi enviado está incorreto", exception.getMessage());
        verify(viacepConnection, never()).consultarCEP(anyString());
    }

    @Test
    @DisplayName("findEnderecoByVIACEP: Deve lançar DataNotFoundException quando CEP não é encontrado pelo VIACEP")
    void testFindEnderecoByVIACEP_NaoEncontradoPeloViacep() {
        // Arrange
        String cep = "99999999";
        when(viacepConnection.consultarCEP(cep)).thenReturn(null); // Simulate Viacep returning null

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> service.findEnderecoByVIACEP(cep));

        assertEquals("Formato de CEP que foi enviado está incorreto", exception.getMessage()); // Service throws this for null response
        verify(viacepConnection).consultarCEP(cep);
    }

    // ===== postEndereco =====
    @Test
    @DisplayName("postEndereco: Deve criar novo endereço com sucesso para CEP válido")
    void testPostEnderecoComSucesso() {
        // Arrange
        when(repository.save(any(Endereco.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Endereco resultado = service.postEndereco(requestPostEndereco);

        // Assert
        assertNotNull(resultado);
        assertEquals("01001000", resultado.getCep());
        assertEquals("Praça da Sé", resultado.getLogradouro());
        verify(repository).save(any(Endereco.class));
    }

    @Test
    @DisplayName("postEndereco: Deve lançar NotAcepptableException para CEP inválido")
    void testPostEndereco_CepInvalido() {
        // Arrange
        RequestPostEndereco requestInvalido = new RequestPostEndereco(
                "123", "Rua X", 100, "", "Bairro", "Cidade", "SP"
        );

        // Act & Assert
        NotAcepptableException exception = assertThrows(NotAcepptableException.class,
            () -> service.postEndereco(requestInvalido));

        assertEquals("Formato de CEP que foi enviado está incorreto", exception.getMessage());
        verify(repository, never()).save(any(Endereco.class));
    }

    // ===== patchComplementoEndereco =====
    @Test
    @DisplayName("patchComplementoEndereco: Deve atualizar complemento com sucesso")
    void testPatchComplementoEndereco() {
        // Arrange
        Endereco enderecoParaAtualizar = endereco;
        enderecoParaAtualizar.setComplemento("Novo Complemento");

        when(repository.findById(requestPatchComplemento.getIdEndereco())).thenReturn(Optional.of(enderecoParaAtualizar));
        when(repository.save(any(Endereco.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Endereco resultado = service.patchComplementoEndereco(requestPatchComplemento);

        // Assert
        assertNotNull(resultado);
        assertEquals("Novo Complemento", resultado.getComplemento());
        verify(repository).findById(requestPatchComplemento.getIdEndereco());
        verify(repository).save(any(Endereco.class));
    }

    @Test
    @DisplayName("patchComplementoEndereco: Deve lançar DataNotFoundException quando endereço não existe")
    void testPatchComplementoEndereco_NaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.patchComplementoEndereco(new RequestPatchComplemento(999, "Novo")));

        assertEquals("Endereço não encontrado!", exception.getMessage());
        verify(repository).findById(999);
        verify(repository, never()).save(any(Endereco.class));
    }

    // ===== patchNumeroEndereco =====
    @Test
    @DisplayName("patchNumeroEndereco: Deve atualizar número com sucesso")
    void testPatchNumeroEndereco() {
        // Arrange
        Endereco enderecoParaAtualizar = endereco;
        enderecoParaAtualizar.setNumero(200);

        when(repository.findById(requestPatchNumero.getId())).thenReturn(Optional.of(enderecoParaAtualizar));
        when(repository.save(any(Endereco.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Endereco resultado = service.patchNumeroEndereco(requestPatchNumero);

        // Assert
        assertNotNull(resultado);
        assertEquals(200, resultado.getNumero());
        verify(repository).findById(requestPatchNumero.getId());
        verify(repository).save(any(Endereco.class));
    }

    @Test
    @DisplayName("patchNumeroEndereco: Deve lançar DataNotFoundException quando endereço não existe")
    void testPatchNumeroEndereco_NaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.patchNumeroEndereco(new RequestPatchNumero(999, 500)));

        assertEquals("Endereço não encontrado!", exception.getMessage());
        verify(repository).findById(999);
        verify(repository, never()).save(any(Endereco.class));
    }

    // ===== putEndereco =====
    @Test
    @DisplayName("putEndereco: Deve atualizar endereço completo com sucesso para CEP válido")
    void testPutEndereco() {
        // Arrange
        Endereco enderecoParaAtualizar = endereco;
        enderecoParaAtualizar.setCep("04538133");
        enderecoParaAtualizar.setLogradouro("Av. Brigadeiro Faria Lima");
        enderecoParaAtualizar.setNumero(1571);

        when(repository.findById(requestPutEndereco.getIdEndereco())).thenReturn(Optional.of(enderecoParaAtualizar));
        when(repository.save(any(Endereco.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Endereco resultado = service.putEndereco(requestPutEndereco);

        // Assert
        assertNotNull(resultado);
        assertEquals("04538133", resultado.getCep());
        assertEquals("Av. Brigadeiro Faria Lima", resultado.getLogradouro());
        assertEquals(1571, resultado.getNumero());
        verify(repository).findById(requestPutEndereco.getIdEndereco());
        verify(repository).save(any(Endereco.class));
    }

    @Test
    @DisplayName("putEndereco: Deve lançar NotAcepptableException para CEP inválido")
    void testPutEndereco_CepInvalido() {
        // Arrange
        RequestPutEndereco requestInvalido = new RequestPutEndereco(
                1, "123", "Rua X", 100, "", "Bairro", "Cidade", "SP"
        );

        // Act & Assert
        NotAcepptableException exception = assertThrows(NotAcepptableException.class,
            () -> service.putEndereco(requestInvalido));

        assertEquals("Formato de CEP que foi enviado está incorreto", exception.getMessage());
        verify(repository, never()).findById(anyInt());
        verify(repository, never()).save(any(Endereco.class));
    }

    @Test
    @DisplayName("putEndereco: Deve lançar DataNotFoundException quando endereço não existe")
    void testPutEndereco_NaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());
        RequestPutEndereco requestComIdInvalido = new RequestPutEndereco(
                999, "04538133", "Av. Brigadeiro Faria Lima", 1571, "", "Bairro", "Cidade", "SP"
        );

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.putEndereco(requestComIdInvalido));

        assertEquals("Endereço não encontrado!", exception.getMessage());
        verify(repository).findById(999);
        verify(repository, never()).save(any(Endereco.class));
    }
}
