package geo.track.service;

import geo.track.domain.Funcionario;
import geo.track.domain.Oficina;
import geo.track.config.GerenciadorTokenJwt;
import geo.track.dto.autenticacao.UsuarioLoginDto;
import geo.track.dto.autenticacao.UsuarioTokenDto;
import geo.track.dto.oficinas.request.OficinaPatchEmailDTO;
import geo.track.dto.oficinas.request.OficinaPatchStatusDTO;
import geo.track.dto.oficinas.request.RequestPutOficina;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.log.LogImplementation;
import geo.track.repository.FuncionarioRepository;
import geo.track.repository.OficinaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private LogImplementation log;

    @Mock
    private PasswordEncoder passwordEncoder; // This mock is not used in OficinaService, but was present in FuncionarioServiceTest
    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private FuncionarioRepository funcionarioRepository;


    @InjectMocks
    private OficinaService service;

    private Oficina oficina;
    private OficinaPatchEmailDTO patchEmailDTO;
    private OficinaPatchStatusDTO patchStatusDTO;
    private UsuarioLoginDto usuarioLoginDto;
    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        // Arrange: Preparar Entidade
        oficina = new Oficina();
        oficina.setIdOficina(1);
        oficina.setRazaoSocial("Oficina do Zé");
        oficina.setCnpj("12345678000199");
        oficina.setEmail("contato@oficinadoze.com");
        oficina.setStatus(false);

        patchEmailDTO = new OficinaPatchEmailDTO(1, "novo.email@oficinadoze.com");
        patchStatusDTO = new OficinaPatchStatusDTO(1, false);

        usuarioLoginDto = new UsuarioLoginDto("test@example.com", "password");

        funcionario = new Funcionario();
        funcionario.setIdFuncionario(1);
        funcionario.setEmail("test@example.com");
        funcionario.setSenha("encodedPassword");
        funcionario.setNome("Test User");
        funcionario.setFkOficina(oficina);
    }

    // ===== cadastrar =====
    @Test
    @DisplayName("cadastrar: Deve cadastrar nova oficina com sucesso quando CNPJ não existe")
    void testCadastrarOficinaComSucesso() {
        // Arrange
        when(repository.findByCnpj(oficina.getCnpj())).thenReturn(Optional.empty());
        when(repository.save(any(Oficina.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Oficina resultado = service.cadastrar(oficina);

        // Assert
        assertNotNull(resultado);
        assertEquals("Oficina do Zé", resultado.getRazaoSocial());
        verify(repository).findByCnpj(oficina.getCnpj());
        verify(repository).save(any(Oficina.class));
    }

    @Test
    @DisplayName("cadastrar: Deve lançar ConflictException quando CNPJ já existe")
    void testCadastrarOficinaComCnpjDuplicado() {
        // Arrange
        when(repository.findByCnpj(oficina.getCnpj())).thenReturn(Optional.of(oficina));

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class,
            () -> service.cadastrar(oficina));

        assertTrue(exception.getMessage().contains("O CNPJ"));
        verify(repository).findByCnpj(oficina.getCnpj());
        verify(repository, never()).save(any(Oficina.class));
    }

    // ===== autenticar =====
    @Test
    @DisplayName("autenticar: Deve autenticar usuário com sucesso e retornar token")
    void testAutenticarComSucesso() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(funcionarioRepository.findByEmail(usuarioLoginDto.getEmail())).thenReturn(Optional.of(funcionario));
        when(gerenciadorTokenJwt.generateToken(authentication, funcionario)).thenReturn("mocked-jwt-token");

        // Act
        UsuarioTokenDto resultado = service.autenticar(usuarioLoginDto);

        // Assert
        assertNotNull(resultado);
        assertEquals("mocked-jwt-token", resultado.getToken());
        assertEquals(funcionario.getEmail(), resultado.getEmail());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(funcionarioRepository).findByEmail(usuarioLoginDto.getEmail());
        verify(gerenciadorTokenJwt).generateToken(authentication, funcionario);
    }

    @Test
    @DisplayName("autenticar: Deve lançar BadCredentialsException para credenciais inválidas")
    void testAutenticarComCredenciaisInvalidas() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> service.autenticar(usuarioLoginDto));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(funcionarioRepository, never()).findByEmail(anyString());
        verify(gerenciadorTokenJwt, never()).generateToken(any(), any());
    }

    @Test
    @DisplayName("autenticar: Deve lançar DataNotFoundException se funcionário não encontrado após autenticação")
    void testAutenticarFuncionarioNaoEncontradoAposAutenticacao() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(funcionarioRepository.findByEmail(usuarioLoginDto.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> service.autenticar(usuarioLoginDto));

        assertEquals("Email do usuário não cadastrado", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(funcionarioRepository).findByEmail(usuarioLoginDto.getEmail());
        verify(gerenciadorTokenJwt, never()).generateToken(any(), any());
    }

    // ===== listar =====
    @Test
    @DisplayName("listar: Deve retornar lista de oficinas quando existem")
    void testListarOficinas() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(oficina));

        // Act
        List<Oficina> resultado = service.listar();

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("listar: Deve retornar lista vazia quando não existem oficinas")
    void testListarOficInasVazia() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act
        List<Oficina> resultado = service.listar();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findAll();
    }

    // ===== findOficinasById =====
    @Test
    @DisplayName("findOficinasById: Deve encontrar oficina por ID com sucesso")
    void testFindOficinasById() {
        // Arrange
        when(repository.findById(1)).thenReturn(Optional.of(oficina));

        // Act
        Oficina resultado = service.findOficinasById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdOficina());
        assertEquals("Oficina do Zé", resultado.getRazaoSocial());
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("findOficinasById: Deve lançar DataNotFoundException quando ID não existe")
    void testFindOficinasById_NaoEncontrada() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.findOficinasById(999));

        verify(repository).findById(999);
    }

    // ===== findOficinasByRazaoSocial =====
    @Test
    @DisplayName("findOficinasByRazaoSocial: Deve encontrar oficinas pela razão social com sucesso")
    void testFindOficinasByRazaoSocial() {
        // Arrange
        String razaoSocial = "Zé";
        when(repository.findByrazaoSocialContainingIgnoreCase(razaoSocial)).thenReturn(List.of(oficina));

        // Act
        List<Oficina> resultado = service.findOficinasByRazaoSocial(razaoSocial);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(repository).findByrazaoSocialContainingIgnoreCase(razaoSocial);
    }

    @Test
    @DisplayName("findOficinasByRazaoSocial: Deve retornar lista vazia quando razão social não existe")
    void testFindOficinasByRazaoSocial_Vazio() {
        // Arrange
        when(repository.findByrazaoSocialContainingIgnoreCase("Inexistente")).thenReturn(List.of());

        // Act
        List<Oficina> resultado = service.findOficinasByRazaoSocial("Inexistente");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findByrazaoSocialContainingIgnoreCase("Inexistente");
    }

    // ===== findOficinasByCnpj =====
    @Test
    @DisplayName("findOficinasByCnpj: Deve encontrar oficina por CNPJ com sucesso")
    void testFindOficinasByCnpj() {
        // Arrange
        String cnpj = "12345678000199";
        when(repository.findByCnpj(cnpj)).thenReturn(Optional.of(oficina));

        // Act
        Oficina resultado = service.findOficinasByCnpj(cnpj);

        // Assert
        assertNotNull(resultado);
        assertEquals(cnpj, resultado.getCnpj());
        verify(repository).findByCnpj(cnpj);
    }

    @Test
    @DisplayName("findOficinasByCnpj: Deve lançar DataNotFoundException quando CNPJ não existe")
    void testFindOficinasByCnpj_NaoEncontrada() {
        // Arrange
        when(repository.findByCnpj("99999999999999")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.findOficinasByCnpj("99999999999999"));

        verify(repository).findByCnpj("99999999999999");
    }

    // ===== atualizar =====
    @Test
    @DisplayName("atualizar: Deve atualizar oficina com sucesso quando existe")
    void testAtualizarOficina() {
        // Arrange
        RequestPutOficina oficinaParaAtualizar = new RequestPutOficina();
        oficinaParaAtualizar.setIdOficina(1);
        oficinaParaAtualizar.setStatus(false);

        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.of(oficina));
        when(repository.save(any(Oficina.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Oficina resultado = service.atualizar(oficinaParaAtualizar);

        // Assert
        assertNotNull(resultado);
        assertEquals(false, resultado.getStatus());
        verify(repository).existsById(1);
        verify(repository).findById(1);
        verify(repository).save(any(Oficina.class));
    }

    @Test
    @DisplayName("atualizar: Deve lançar DataNotFoundException quando oficina não existe")
    void testAtualizarOficina_NaoEncontrada() {
        // Arrange
        RequestPutOficina oficinaParaAtualizar = new RequestPutOficina();
        oficinaParaAtualizar.setIdOficina(999);
        when(repository.existsById(999)).thenReturn(false);

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.atualizar(oficinaParaAtualizar));

        verify(repository).existsById(999);
        verify(repository, never()).save(any(Oficina.class));
    }

    // ===== patchEmail =====
    @Test
    @DisplayName("patchEmail: Deve atualizar email da oficina com sucesso")
    void testPatchEmail() {
        // Arrange
        Oficina oficinaParaAtualizar = oficina;
        oficinaParaAtualizar.setEmail("novo.email@oficinadoze.com");

        when(repository.findById(patchEmailDTO.getId())).thenReturn(Optional.of(oficinaParaAtualizar));
        when(repository.save(any(Oficina.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Oficina resultado = service.patchEmail(patchEmailDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("novo.email@oficinadoze.com", resultado.getEmail());
        verify(repository).findById(patchEmailDTO.getId());
        verify(repository).save(any(Oficina.class));
    }

    @Test
    @DisplayName("patchEmail: Deve lançar DataNotFoundException quando oficina não existe")
    void testPatchEmail_NaoEncontrada() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.patchEmail(new OficinaPatchEmailDTO(999, "novo@email.com")));

        verify(repository).findById(999);
        verify(repository, never()).save(any(Oficina.class));
    }

    // ===== patchStatus =====
    @Test
    @DisplayName("patchStatus: Deve atualizar status da oficina com sucesso")
    void testPatchStatus() {
        // Arrange
        Oficina oficinaParaAtualizar = oficina;
        oficinaParaAtualizar.setStatus(true);

        when(repository.findById(patchStatusDTO.getId())).thenReturn(Optional.of(oficinaParaAtualizar));
        when(repository.save(any(Oficina.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Oficina resultado = service.patchStatus(patchStatusDTO);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.getStatus());
        verify(repository).findById(patchStatusDTO.getId());
        verify(repository).save(any(Oficina.class));
    }

    @Test
    @DisplayName("patchStatus: Deve lançar DataNotFoundException quando oficina não existe")
    void testPatchStatus_NaoEncontrada() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.patchStatus(new OficinaPatchStatusDTO(999, true)));

        verify(repository).findById(999);
        verify(repository, never()).save(any(Oficina.class));
    }

    // ===== remover =====
    @Test
    @DisplayName("remover: Deve remover oficina com sucesso quando existe")
    void testRemoverOficina() {
        // Arrange
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        // Act
        assertDoesNotThrow(() -> service.remover(1));

        // Assert
        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("remover: Deve lançar DataNotFoundException quando oficina não existe")
    void testRemoverOficina_NaoEncontrada() {
        // Arrange
        when(repository.existsById(999)).thenReturn(false);

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.remover(999));

        verify(repository).existsById(999);
        verify(repository, never()).deleteById(999);
    }
}
