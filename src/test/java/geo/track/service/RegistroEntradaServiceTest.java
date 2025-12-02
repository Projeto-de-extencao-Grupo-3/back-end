package geo.track.service;

import geo.track.domain.RegistroEntrada;
import geo.track.domain.Veiculos;
import geo.track.dto.registroEntrada.request.RequestPostEntradaAgendada;
import geo.track.dto.registroEntrada.request.RequestPutRegistroEntrada;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.ForbiddenException;
import geo.track.repository.RegistroEntradaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do RegistroEntradaService")
class RegistroEntradaServiceTest {

    @Mock
    private RegistroEntradaRepository repository;

    @Mock
    private VeiculosService veiculosService;

    @InjectMocks
    private RegistroEntradaService service;

    private RegistroEntrada registroEntrada;
    private Veiculos veiculo;
    private RequestPostEntradaAgendada postRegistroEntrada;
    private RequestPutRegistroEntrada putRegistroEntrada;

    @BeforeEach
    void setUp() {
        veiculo = new Veiculos();
        veiculo.setIdVeiculo(1);

        registroEntrada = new RegistroEntrada();
        registroEntrada.setIdRegistroEntrada(1);
        registroEntrada.setDataEntradaPrevista(LocalDate.now().plusDays(1));
        registroEntrada.setFkVeiculo(veiculo);

        postRegistroEntrada = new RequestPostEntradaAgendada(LocalDate.now().plusDays(1), 1);
        putRegistroEntrada = new RequestPutRegistroEntrada(
                1,
                LocalDate.now(),
                "João Responsável",
                "123.456.789-00",
                1, 1, 1, 1, 1, 1,1,1,
                1, ""
        );
    }

    // --- Testes para postRegistro ---
    @Test
    @DisplayName("postRegistro: Deve criar um registro de entrada com sucesso")
    void deveCriarRegistroComSucesso() {
        when(veiculosService.findVeiculoById(any(Integer.class))).thenReturn(veiculo);
        when(repository.save(any(RegistroEntrada.class))).thenAnswer(inv -> {
            RegistroEntrada re = inv.getArgument(0);
            re.setIdRegistroEntrada(1);
            return re;
        });

        RegistroEntrada resultado = service.realizarAgendamentoVeiculo(postRegistroEntrada);

        assertNotNull(resultado);
        assertEquals(postRegistroEntrada.getDtEntradaPrevista(), resultado.getDataEntradaPrevista());
        verify(repository).save(any(RegistroEntrada.class));
    }

    // --- Testes para findRegistro ---
    @Test
    @DisplayName("findRegistro: Deve retornar uma lista de registros")
    void deveRetornarListaDeRegistros() {
        when(repository.findAll()).thenReturn(List.of(registroEntrada));
        List<RegistroEntrada> resultado = service.findRegistros();
        assertFalse(resultado.isEmpty());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("findRegistro: Deve retornar uma lista vazia quando não houver registros")
    void deveRetornarListaVazia() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<RegistroEntrada> resultado = service.findRegistros();
        assertTrue(resultado.isEmpty());
        verify(repository).findAll();
    }

    // --- Testes para findRegistroById ---
    @Test
    @DisplayName("findRegistroById: Deve encontrar registro por ID com sucesso")
    void deveEncontrarRegistroPorIdComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(registroEntrada));
        RegistroEntrada resultado = service.findRegistroById(1);
        assertNotNull(resultado);
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("findRegistroById: Deve lançar DataNotFoundException para ID inexistente")
    void deveLancarExcecaoAoBuscarIdInexistente() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> service.findRegistroById(99));
        verify(repository).findById(99);
    }

    // --- Testes para putRegistro ---
    @Test
    @DisplayName("putRegistro: Deve atualizar um registro com sucesso")
    void deveAtualizarRegistroComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(registroEntrada));
        when(repository.save(any(RegistroEntrada.class))).thenAnswer(inv -> inv.getArgument(0));

        RegistroEntrada resultado = service.putRegistro(putRegistroEntrada);

        assertNotNull(resultado);
        assertEquals(putRegistroEntrada.getResponsavel(), resultado.getResponsavel());
        assertEquals(putRegistroEntrada.getCpf(), resultado.getCpf());
        verify(repository).findById(1);
        verify(repository).save(any(RegistroEntrada.class));
    }

    @Test
    @DisplayName("putRegistro: Deve lançar DataNotFoundException para ID inexistente")
    void deveLancarExcecaoAoAtualizarRegistroInexistente() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        putRegistroEntrada.setIdRegistro(99);
        assertThrows(DataNotFoundException.class, () -> service.putRegistro(putRegistroEntrada));
        verify(repository).findById(99);
        verify(repository, never()).save(any(RegistroEntrada.class));
    }

    @Test
    @DisplayName("deleteRegistro: Deve lançar DataNotFoundException ao tentar deletar registro inexistente")
    void deveLancarDataNotFoundExceptionAoDeletarRegistroInexistente() {
        when(repository.existsById(any(Integer.class))).thenReturn(false);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            service.deletarRegistro(99);
        });

        assertEquals("Registro de Entrada não encontrado", exception.getMessage());
        verify(repository).existsById(any(Integer.class));
        verify(repository, never()).deleteById(anyInt());
    }
}