package geo.track.service;

import geo.track.domain.Oficinas;
import geo.track.config.GerenciadorTokenJwt;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    private OficinaPatchEmailDTO patchEmailDTO;
    private OficinaPatchStatusDTO patchStatusDTO;

    @BeforeEach
    void setUp() {
        oficina = new Oficinas();
        oficina.setIdOficina(1);
        oficina.setRazaoSocial("Oficina do Zé");
        oficina.setCnpj("12345678000199");
        oficina.setEmail("contato@oficinadoze.com");
        oficina.setSenha("senha");
        oficina.setStatus(false);

        patchEmailDTO = new OficinaPatchEmailDTO(1, "novo.email@oficinadoze.com");
        patchStatusDTO = new OficinaPatchStatusDTO(1, false);
    }

    // --- Testes para cadastrar ---

    @Test
    @DisplayName("Deve cadastrar uma nova oficina com sucesso")
    void deveCadastrarOficinaComSucesso() {
        // Arrange
        when(repository.findByCnpj(oficina.getCnpj())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(oficina.getSenha())).thenReturn("senhaCriptografada");
        when(repository.save(any(Oficinas.class))).thenReturn(oficina);

        // Act
        Oficinas resultado = service.cadastrar(oficina);

        // Assert
        assertNotNull(resultado);
        verify(repository).findByCnpj(oficina.getCnpj());
        verify(passwordEncoder).encode("senha");
        verify(repository).save(oficina);
    }

    @Test
    @DisplayName("cadastrar: Deve lançar ConflictException ao tentar cadastrar CNPJ duplicado")
    void deveLancarConflictExceptionAoCadastrarCnpjDuplicado() {
        when(repository.findByCnpj(oficina.getCnpj())).thenReturn(Optional.of(oficina));

        ConflictException exception = assertThrows(ConflictException.class, () -> {
            service.cadastrar(oficina);
        });

        assertTrue(exception.getMessage().contains("O CNPJ %s já está cadastrado!".formatted(oficina.getCnpj())));
        verify(repository).findByCnpj(oficina.getCnpj());
        verify(passwordEncoder, never()).encode(anyString());
        verify(repository, never()).save(any(Oficinas.class));
    }

    // --- Testes para listar ---
    @Test
    @DisplayName("listar: Deve retornar uma lista de oficinas")
    void deveRetornarListaDeOficinas() {
        when(repository.findAll()).thenReturn(List.of(oficina));
        List<Oficinas> resultado = service.listar();
        assertFalse(resultado.isEmpty());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("listar: Deve retornar uma lista vazia quando não houver oficinas")
    void deveRetornarListaVazia() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<Oficinas> resultado = service.listar();
        assertTrue(resultado.isEmpty());
        verify(repository).findAll();
    }

    // --- Testes para findOficinasById ---
    @Test
    @DisplayName("findOficinasById: Deve encontrar oficina por ID com sucesso")
    void deveEncontrarOficinaPorIdComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(oficina));
        Oficinas resultado = service.findOficinasById(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdOficina());
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("findOficinasById: Deve lançar DataNotFoundException para ID inexistente")
    void deveLancarExcecaoAoBuscarIdInexistente() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> service.findOficinasById(99));
        verify(repository).findById(99);
    }

    // --- Testes para findOficinasByRazaoSocial ---
    @Test
    @DisplayName("findOficinasByRazaoSocial: Deve encontrar oficinas pela razão social")
    void deveEncontrarOficinasPorRazaoSocial() {
        when(repository.findByrazaoSocialContainingIgnoreCase("Zé")).thenReturn(List.of(oficina));
        List<Oficinas> resultado = service.findOficinasByRazaoSocial("Zé");
        assertFalse(resultado.isEmpty());
        verify(repository).findByrazaoSocialContainingIgnoreCase("Zé");
    }

    // --- Testes para findOficinasByCnpj ---
    @Test
    @DisplayName("findOficinasByCnpj: Deve encontrar oficina por CNPJ com sucesso")
    void deveEncontrarOficinaPorCnpjComSucesso() {
        when(repository.findByCnpj(oficina.getCnpj())).thenReturn(Optional.of(oficina));
        Oficinas resultado = service.findOficinasByCnpj(oficina.getCnpj());
        assertNotNull(resultado);
        verify(repository).findByCnpj(oficina.getCnpj());
    }

    // --- Testes para atualizar ---
    @Test
    @DisplayName("atualizar: Deve atualizar uma oficina com sucesso")
    void deveAtualizarOficinaComSucesso() {
        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(any(Oficinas.class))).thenReturn(oficina);
        Oficinas resultado = service.atualizar(1, oficina);
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdOficina());
        verify(repository).existsById(1);
        verify(repository).save(oficina);
    }

    // --- Testes para patchEmail ---
    @Test
    @DisplayName("patchEmail: Deve atualizar o email com sucesso")
    void deveAtualizarEmailComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(oficina));
        when(repository.save(any(Oficinas.class))).thenAnswer(inv -> inv.getArgument(0));
        Oficinas resultado = service.patchEmail(patchEmailDTO);
        assertEquals(patchEmailDTO.getEmail(), resultado.getEmail());
        verify(repository).findById(1);
        verify(repository).save(any(Oficinas.class));
    }

    // --- Testes para patchStatus ---
    @Test
    @DisplayName("patchStatus: Deve atualizar o status com sucesso")
    void deveAtualizarStatusComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(oficina));
        when(repository.save(any(Oficinas.class))).thenAnswer(inv -> inv.getArgument(0));
        Oficinas resultado = service.patchStatus(patchStatusDTO);
        assertEquals(patchStatusDTO.getStatus(), resultado.getStatus());
        verify(repository).findById(1);
        verify(repository).save(any(Oficinas.class));
    }

    // --- Testes para remover ---
    @Test
    @DisplayName("remover: Deve remover uma oficina com sucesso")
    void deveRemoverOficinaComSucesso() {
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);
        assertDoesNotThrow(() -> service.remover(1));
        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("remover: Deve lançar DataNotFoundException ao tentar remover oficina inexistente")
    void deveLancarExcecaoAoRemoverOficinaInexistente() {
        when(repository.existsById(99)).thenReturn(false);
        assertThrows(DataNotFoundException.class, () -> service.remover(99));
        verify(repository).existsById(99);
        verify(repository, never()).deleteById(anyInt());
    }
}