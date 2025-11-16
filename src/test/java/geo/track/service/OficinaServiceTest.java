package geo.track.service;

import geo.track.config.GerenciadorTokenJwt;
import geo.track.domain.Oficinas;
import geo.track.dto.autenticacao.UsuarioTokenDto;
import geo.track.dto.oficinas.request.OficinaPatchEmailDTO;
import geo.track.dto.oficinas.request.OficinaPatchStatusDTO;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.OficinaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do OficinaService")
class OficinaServiceTest {

    @Mock
    private OficinaRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private OficinaService service;

    private Oficinas oficina;

    @BeforeEach
    void setUp() {
        oficina = new Oficinas();
        oficina.setIdOficina(1);
        oficina.setCnpj("12345678000199");
        oficina.setRazaoSocial("Oficina Teste LTDA");
        oficina.setEmail("teste@oficina.com");
        oficina.setSenha("senha123");
        oficina.setStatus("ATIVO");
    }

    @Test
    @DisplayName("Deve cadastrar uma nova oficina com sucesso")
    void deveCadastrarOficinaComSucesso() {
        // Arrange
        when(repository.findByCnpj(oficina.getCnpj())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("senhaCriptografada");
        when(repository.save(any(Oficinas.class))).thenReturn(oficina);

        // Act
        Oficinas resultado = service.cadastrar(oficina);

        // Assert
        assertNotNull(resultado);
        verify(repository).findByCnpj(oficina.getCnpj());
        verify(passwordEncoder).encode("senha123");
        verify(repository).save(oficina);
    }

    @Test
    @DisplayName("Deve lançar ConflictException ao cadastrar CNPJ duplicado")
    void deveLancarExcecaoAoCadastrarCnpjDuplicado() {
        // Arrange
        when(repository.findByCnpj(oficina.getCnpj())).thenReturn(Optional.of(oficina));

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class,
                () -> service.cadastrar(oficina));

        assertTrue(exception.getMessage().contains(oficina.getCnpj()));
        verify(repository).findByCnpj(oficina.getCnpj());
    }

    @Test
    @DisplayName("Deve autenticar oficina com sucesso")
    void deveAutenticarOficinaComSucesso() {
        // Arrange
        Authentication auth = mock(Authentication.class);
        String token = "token-jwt-gerado";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(repository.findByCnpj(oficina.getCnpj())).thenReturn(Optional.of(oficina));
        when(gerenciadorTokenJwt.generateToken(auth)).thenReturn(token);

        // Act
        UsuarioTokenDto resultado = service.autenticar(oficina);

        // Assert
        assertNotNull(resultado);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(repository).findByCnpj(oficina.getCnpj());
        verify(gerenciadorTokenJwt).generateToken(auth);
    }

    @Test
    @DisplayName("Deve lançar exceção ao autenticar com CNPJ não cadastrado")
    void deveLancarExcecaoAoAutenticarCnpjNaoCadastrado() {
        // Arrange
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);
        when(repository.findByCnpj(oficina.getCnpj())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> service.autenticar(oficina));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(repository).findByCnpj(oficina.getCnpj());
    }

    @Test
    @DisplayName("Deve listar todas as oficinas")
    void deveListarTodasOficinas() {
        // Arrange
        List<Oficinas> oficinas = Arrays.asList(oficina, new Oficinas());
        when(repository.findAll()).thenReturn(oficinas);

        // Act
        List<Oficinas> resultado = service.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve buscar oficina por ID com sucesso")
    void deveBuscarOficinaPorIdComSucesso() {
        // Arrange
        when(repository.findById(1)).thenReturn(Optional.of(oficina));

        // Act
        Oficinas resultado = service.findOficinasById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(oficina.getIdOficina(), resultado.getIdOficina());
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("Deve lançar DataNotFoundException ao buscar ID inexistente")
    void deveLancarExcecaoAoBuscarIdInexistente() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> service.findOficinasById(999));

        assertTrue(exception.getMessage().contains("999"));
        verify(repository).findById(999);
    }

    @Test
    @DisplayName("Deve buscar oficinas por razão social")
    void deveBuscarOficinasPorRazaoSocial() {
        // Arrange
        List<Oficinas> oficinas = Arrays.asList(oficina);
        when(repository.findByrazaoSocialContainingIgnoreCase("Teste"))
                .thenReturn(oficinas);

        // Act
        List<Oficinas> resultado = service.findOficinasByRazaoSocial("Teste");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository).findByrazaoSocialContainingIgnoreCase("Teste");
    }

    @Test
    @DisplayName("Deve buscar oficina por CNPJ com sucesso")
    void deveBuscarOficinaPorCnpjComSucesso() {
        // Arrange
        when(repository.findByCnpj(oficina.getCnpj())).thenReturn(Optional.of(oficina));

        // Act
        Oficinas resultado = service.findOficinasByCnpj(oficina.getCnpj());

        // Assert
        assertNotNull(resultado);
        assertEquals(oficina.getCnpj(), resultado.getCnpj());
        verify(repository).findByCnpj(oficina.getCnpj());
    }

    @Test
    @DisplayName("Deve lançar DataNotFoundException ao buscar CNPJ inexistente")
    void deveLancarExcecaoAoBuscarCnpjInexistente() {
        // Arrange
        String cnpj = "99999999000199";
        when(repository.findByCnpj(cnpj)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> service.findOficinasByCnpj(cnpj));

        assertTrue(exception.getMessage().contains(cnpj));
        verify(repository).findByCnpj(cnpj);
    }

    @Test
    @DisplayName("Deve atualizar oficina com sucesso")
    void deveAtualizarOficinaComSucesso() {
        // Arrange
        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(any(Oficinas.class))).thenReturn(oficina);

        // Act
        Oficinas resultado = service.atualizar(1, oficina);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, oficina.getIdOficina());
        verify(repository).existsById(1);
        verify(repository).save(oficina);
    }

    @Test
    @DisplayName("Deve lançar DataNotFoundException ao atualizar ID inexistente")
    void deveLancarExcecaoAoAtualizarIdInexistente() {
        // Arrange
        when(repository.existsById(999)).thenReturn(false);

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> service.atualizar(999, oficina));

        assertTrue(exception.getMessage().contains("999"));
        verify(repository).existsById(999);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar email da oficina com sucesso")
    void deveAtualizarEmailComSucesso() {
        // Arrange
        OficinaPatchEmailDTO dto = new OficinaPatchEmailDTO();
        dto.setId(1);
        dto.setEmail("teste@oficina.com");

        when(repository.findById(1)).thenReturn(Optional.of(oficina));
        when(repository.save(any(Oficinas.class))).thenReturn(oficina);

        // Act
        Oficinas resultado = service.patchEmail(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("teste@oficina.com", resultado.getEmail());
        verify(repository).findById(1);
        verify(repository).save(oficina);
    }

    @Test
    @DisplayName("Deve lançar DataNotFoundException ao atualizar email de ID inexistente")
    void deveLancarExcecaoAoAtualizarEmailIdInexistente() {
        // Arrange
        OficinaPatchEmailDTO dto = new OficinaPatchEmailDTO();
        dto.setId(999);
        dto.setEmail("email@teste.com");

        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> service.patchEmail(dto));

        assertTrue(exception.getMessage().contains("oficina"));
        verify(repository).findById(999);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar status da oficina com sucesso")
    void deveAtualizarStatusComSucesso() {
        // Arrange
        OficinaPatchStatusDTO dto = new OficinaPatchStatusDTO();
        dto.setId(1);
        dto.setStatus("INATIVO");

        when(repository.findById(1)).thenReturn(Optional.of(oficina));
        when(repository.save(any(Oficinas.class))).thenReturn(oficina);

        // Act
        Oficinas resultado = service.patchStatus(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("INATIVO", resultado.getStatus());
        verify(repository).findById(1);
        verify(repository).save(oficina);
    }

    @Test
    @DisplayName("Deve lançar DataNotFoundException ao atualizar status de ID inexistente")
    void deveLancarExcecaoAoAtualizarStatusIdInexistente() {
        // Arrange
        OficinaPatchStatusDTO dto = new OficinaPatchStatusDTO();
        dto.setId(999);
        dto.setStatus("INATIVO");

        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> service.patchStatus(dto));

        assertTrue(exception.getMessage().contains("oficina"));
        verify(repository).findById(999);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve remover oficina com sucesso")
    void deveRemoverOficinaComSucesso() {
        // Arrange
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        // Act
        service.remover(1);

        // Assert
        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("Deve lançar DataNotFoundException ao remover ID inexistente")
    void deveLancarExcecaoAoRemoverIdInexistente() {
        // Arrange
        when(repository.existsById(999)).thenReturn(false);

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> service.remover(999));

        assertTrue(exception.getMessage().contains("999"));
        verify(repository).existsById(999);
        verify(repository, never()).deleteById(any());
    }
}