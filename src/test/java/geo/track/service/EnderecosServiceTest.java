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

    private Enderecos endereco;
    private RequestPostEndereco requestPostEndereco;
    private RequestPatchComplemento requestPatchComplemento;
    private RequestPatchNumero requestPatchNumero;
    private RequestPutEndereco requestPutEndereco;

    @BeforeEach
    void setUp() {
        endereco = new Enderecos();
        endereco.setIdEndereco(1);
        endereco.setComplemento("Bloco 25 Apartamento 51");
        endereco.setCep("00000000");
        endereco.setCidade("São Paulo");
        endereco.setEstado("São Paulo");
        endereco.setBairro("Jardim Taquaral");
        endereco.setLogradouro("Avenida Brazil");
        endereco.setNumero(190);

        requestPostEndereco = new RequestPostEndereco(
                "00000000",
                "Avenida Brazil",
                190,
                "Bloco 25 Apartamento 51",
                "Jardim Taquaral",
                "São Paulo",
                "São Paulo"
        );

        requestPatchComplemento = new RequestPatchComplemento(1, "Novo Complemento");

        requestPatchNumero = new RequestPatchNumero(1, 999);

        requestPutEndereco = new RequestPutEndereco(
                1,
                "11111111",
                "Nova Rua",
                123,
                "Nova Casa",
                "Novo Bairro",
                "Nova Cidade",
                "Novo Estado"
        );
    }

    @Test
    @DisplayName("Deve encontrar um endereço com sucesso")
    void deveEncontrarUmEnderecoComSucesso() {
        Integer idDesejado = 1;

        when(repository.findById(idDesejado)).thenReturn(Optional.of(endereco));

        Enderecos resultadoBusca = service.findEnderecoById(idDesejado);

        assertNotNull(resultadoBusca);
        verify(repository).findById(idDesejado);
    }

    @Test
    @DisplayName("Deve lançar DataNotFoundException ao buscar ID inexistente")
    void deveLancarExcecaoAoBuscarEnderecoNaoExistente() {
        Integer idDesejado = 2;

        when(repository.findById(idDesejado)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.findEnderecoById(idDesejado);
        });

        assertTrue(exception.getMessage().contains("ID " + idDesejado));
        verify(repository).findById(idDesejado);
    }

    @Test
    @DisplayName("Deve encontrar um endereço com sucesso pelo ViaCEP")
    void deveEncontrarUmEnderecoComSucessoPeloViacep() {
        String cepDesejado = "00000000";

        ResponseViacep responseEsperada = new ResponseViacep("00000000", "Avenida Brazil", "Jardim Taquaral", "São Paulo", "São Paulo");

        when(viacepConnection.consultarCEP(cepDesejado)).thenReturn(responseEsperada);

        ResponseViacep responseResultado = service.findEnderecoByVIACEP(cepDesejado);

        assertNotNull(responseResultado);
        verify(viacepConnection).consultarCEP(cepDesejado);
    }

    @Test
    @DisplayName("Deve lançar exceção quando CEP for diferente de 8 caracteres na busca ViaCEP")
    void deveLancarExcecaoQuandoCepDiferenteDeOito() {
        String cepDesejado = "0000000";

        NotAcepptableException responseResultado = assertThrows(NotAcepptableException.class, () -> {
            service.findEnderecoByVIACEP(cepDesejado);
        });

        assertTrue(responseResultado.getMessage().contains("Envie um CEP que possua 8 caracteres"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando CEP não for encontrado na busca ViaCEP")
    void deveLancarExcecaoQuandoCepNaoEncontrado() {
        String cepDesejado = "00000001";

        when(viacepConnection.consultarCEP(cepDesejado)).thenThrow(new DataNotFoundException("CEP: %s não foi encontrado".formatted(cepDesejado), "Endereços"));

        assertThrows(DataNotFoundException.class, () -> {
            service.findEnderecoByVIACEP(cepDesejado);
        });

        verify(viacepConnection).consultarCEP(cepDesejado);
    }

    @Test
    @DisplayName("Deve criar um novo endereço com sucesso")
    void devePostarEnderecoComSucesso() {
        // Arrange
        when(repository.save(any(Enderecos.class))).thenReturn(endereco);

        // Act
        Enderecos enderecoSalvo = service.postEndereco(requestPostEndereco);

        // Assert
        assertNotNull(enderecoSalvo);
        assertEquals(requestPostEndereco.getCep(), enderecoSalvo.getCep());
        verify(repository).save(any(Enderecos.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar endereço com CEP inválido")
    void deveLancarExcecaoAoPostarEnderecoComCepInvalido() {
        // Arrange
        requestPostEndereco.setCep("12345");

        // Act
        NotAcepptableException exception = assertThrows(NotAcepptableException.class, () -> {
            service.postEndereco(requestPostEndereco);
        });

        // Assert
        assertTrue(exception.getMessage().contains("Envie um CEP que possua 8 caracteres"));
        verify(repository, never()).save(any(Enderecos.class));
    }

    @Test
    @DisplayName("Deve atualizar o complemento do endereço com sucesso")
    void deveAtualizarComplementoComSucesso() {
        // Arrange
        when(repository.findById(requestPatchComplemento.getId())).thenReturn(Optional.of(endereco));
        when(repository.save(any(Enderecos.class))).thenReturn(endereco);

        // Act
        Enderecos enderecoAtualizado = service.patchComplementoEndereco(requestPatchComplemento);

        // Assert
        assertNotNull(enderecoAtualizado);
        assertEquals(requestPatchComplemento.getComplemento(), enderecoAtualizado.getComplemento());
        verify(repository).findById(requestPatchComplemento.getId());
        verify(repository).save(any(Enderecos.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar complemento de ID inexistente")
    void deveLancarExcecaoAoAtualizarComplementoDeIdInexistente() {
        // Arrange
        when(repository.findById(requestPatchComplemento.getId())).thenReturn(Optional.empty());

        // Act
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.patchComplementoEndereco(requestPatchComplemento);
        });

        // Assert
        assertTrue(exception.getMessage().contains("Endereço com o ID %d não foi encontrado".formatted(requestPatchComplemento.getId())));
        verify(repository).findById(requestPatchComplemento.getId());
        verify(repository, never()).save(any(Enderecos.class));
    }

    @Test
    @DisplayName("Deve atualizar o número do endereço com sucesso")
    void deveAtualizarNumeroComSucesso() {
        // Arrange
        when(repository.findById(requestPatchNumero.getId())).thenReturn(Optional.of(endereco));
        when(repository.save(any(Enderecos.class))).thenReturn(endereco);

        // Act
        Enderecos enderecoAtualizado = service.patchNumeroEndereco(requestPatchNumero);

        // Assert
        assertNotNull(enderecoAtualizado);
        assertEquals(requestPatchNumero.getNumero(), enderecoAtualizado.getNumero());
        verify(repository).findById(requestPatchNumero.getId());
        verify(repository).save(any(Enderecos.class));
    }

    @Test
    @DisplayName("Deve retornar nulo ao tentar atualizar número de ID inexistente")
    void deveRetornarNuloAoAtualizarNumeroDeIdInexistente() {
        // Arrange
        when(repository.findById(requestPatchNumero.getId())).thenReturn(Optional.empty());

        // Act
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.patchNumeroEndereco(requestPatchNumero);
        });

        // Assert
        assertTrue(exception.getMessage().contains("Endereço com o ID %d não foi encontrado".formatted(requestPatchComplemento.getId())));
        verify(repository).findById(requestPatchNumero.getId());
        verify(repository, never()).save(any(Enderecos.class));
    }

    @Test
    @DisplayName("Deve atualizar o endereço completo com sucesso")
    void deveAtualizarEnderecoCompletoComSucesso() {
        // Arrange
        when(repository.findById(requestPutEndereco.getId())).thenReturn(Optional.of(endereco));
        when(repository.save(any(Enderecos.class))).thenReturn(endereco);

        // Act
        Enderecos enderecoAtualizado = service.putEndereco(requestPutEndereco);

        // Assert
        assertNotNull(enderecoAtualizado);
        assertEquals(requestPutEndereco.getCep(), enderecoAtualizado.getCep());
        assertEquals(requestPutEndereco.getLogradouro(), enderecoAtualizado.getLogradouro());
        verify(repository).findById(requestPutEndereco.getId());
        verify(repository).save(any(Enderecos.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar endereço completo de ID inexistente")
    void deveLancarExcecaoAoAtualizarEnderecoCompletoDeIdInexistente() {
        // Arrange
        when(repository.findById(requestPutEndereco.getId())).thenReturn(Optional.empty());

        // Act
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.putEndereco(requestPutEndereco);
        });

        // Assert
        assertTrue(exception.getMessage().contains("Endereço com o ID %d não foi encontrado".formatted(requestPutEndereco.getId())));
        verify(repository).findById(requestPutEndereco.getId());
        verify(repository, never()).save(any(Enderecos.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar endereço completo com CEP inválido")
    void deveLancarExcecaoAoAtualizarEnderecoCompletoComCepInvalido() {
        // Arrange
        requestPutEndereco.setCep("12345");

        // Act
        NotAcepptableException exception = assertThrows(NotAcepptableException.class, () -> {
            service.putEndereco(requestPutEndereco);
        });

        // Assert
        assertTrue(exception.getMessage().contains("Envie um CEP que possua 8 caracteres"));
        verify(repository, never()).findById(requestPutEndereco.getId());
        verify(repository, never()).save(any(Enderecos.class));
    }
}