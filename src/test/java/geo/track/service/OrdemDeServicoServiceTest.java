package geo.track.service;

import geo.track.domain.ItemServico;
import geo.track.domain.OrdemDeServico;
import geo.track.domain.RegistroEntrada;
import geo.track.dto.os.request.*;
import geo.track.enums.os.StatusVeiculo;
import geo.track.exception.BadRequestException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.OrdemDeServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do OrdemDeServicoService")
class OrdemDeServicoServiceTest {

    @Mock
    private OrdemDeServicoRepository ordemRepository;

    @Mock
    private ItemServicoService itemServicoService;

    @Mock
    private RegistroEntradaService registroEntradaService;

    @InjectMocks
    private OrdemDeServicoService service;

    private OrdemDeServico ordemDeServicos;
    private RegistroEntrada registroEntrada;
    private PostEntradaVeiculo postEntradaVeiculo;
    private RequestPutValorESaida requestPutValorESaida;
    private RequestPatchSaidaEfetiva requestPatchSaidaEfetiva;
    private RequestPatchStatus requestPatchStatus;
    private RequestPatchSeguradora requestPatchSeguradora;
    private RequestPatchNfRealizada requestPatchNfRealizada;
    private RequestPatchPagtoRealizado requestPatchPagtoRealizado;

    @BeforeEach
    void setUp() {
        // Arrange: Preparar Entidades
        registroEntrada = new RegistroEntrada();
        registroEntrada.setIdRegistroEntrada(1);

        ordemDeServicos = OrdemDeServico.builder()
                .idOrdemServico(1)
                .valorTotal(500.0)
                .status(StatusVeiculo.EM_PRODUCAO)
                .nfRealizada(true)
                .dtSaidaPrevista(LocalDate.now().plusMonths(1))
                .fk_entrada(registroEntrada)
                .build();

        postEntradaVeiculo = new PostEntradaVeiculo(StatusVeiculo.EM_PRODUCAO, 500.0, 1);
        requestPutValorESaida = new RequestPutValorESaida(1, 750.0, LocalDate.now().plusWeeks(5), 1);
        requestPatchSaidaEfetiva = new RequestPatchSaidaEfetiva(1, 1, LocalDate.now().plusWeeks(6));
        requestPatchStatus = new RequestPatchStatus(1, StatusVeiculo.FINALIZADO, 1);
        requestPatchSeguradora = new RequestPatchSeguradora(1, 1, true);
        requestPatchNfRealizada = new RequestPatchNfRealizada(1, 1, true);
        requestPatchPagtoRealizado = new RequestPatchPagtoRealizado(1, 1, true);
    }

    // ===== postOrdem =====
    @Test
    @DisplayName("postOrdem: Deve criar ordem de serviço com sucesso")
    void testPostOrdemComSucesso() {
        // Arrange
        when(registroEntradaService.findRegistroById(1)).thenReturn(registroEntrada);
        when(ordemRepository.save(any(OrdemDeServico.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        OrdemDeServico resultado = service.postOrdem(postEntradaVeiculo);

        // Assert
        assertNotNull(resultado);
        assertEquals(500.0, resultado.getValorTotal());
        verify(registroEntradaService).findRegistroById(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // ===== findOrdem =====
    @Test
    @DisplayName("findOrdem: Deve retornar lista de ordens de serviço quando existem")
    void testFindOrdemComResultados() {
        // Arrange
        when(ordemRepository.findAll()).thenReturn(List.of(ordemDeServicos));

        // Act
        List<OrdemDeServico> resultado = service.findOrdem();

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(ordemRepository).findAll();
    }

    @Test
    @DisplayName("findOrdem: Deve retornar lista vazia quando não existem ordens")
    void testFindOrdemSemResultados() {
        // Arrange
        when(ordemRepository.findAll()).thenReturn(List.of());

        // Act
        List<OrdemDeServico> resultado = service.findOrdem();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(ordemRepository).findAll();
    }

    // ===== findOrdemById =====
    @Test
    @DisplayName("findOrdemById: Deve encontrar ordem por ID com sucesso")
    void testFindOrdemById() {
        // Arrange
        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemDeServicos));

        // Act
        OrdemDeServico resultado = service.findOrdemById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdOrdemServico());
        verify(ordemRepository).findById(1);
    }

    @Test
    @DisplayName("findOrdemById: Deve lançar DataNotFoundException quando ID não existe")
    void testFindOrdemById_NaoEncontrada() {
        // Arrange
        when(ordemRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.findOrdemById(999));

        verify(ordemRepository).findById(999);
    }

    // ===== putValorESaida =====
    @Test
    @DisplayName("putValorESaida: Deve atualizar valor e data de saída com sucesso")
    void testPutValorESaidaComSucesso() {
        // Arrange
        OrdemDeServico ordemParaAtualizar = ordemDeServicos;
        ordemParaAtualizar.setValorTotal(750.0);
        ordemParaAtualizar.setDtSaidaPrevista(LocalDate.now().plusWeeks(5));

        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemParaAtualizar));
        when(ordemRepository.save(any(OrdemDeServico.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        OrdemDeServico resultado = service.putValorESaida(requestPutValorESaida);

        // Assert
        assertNotNull(resultado);
        assertEquals(750.0, resultado.getValorTotal());
        verify(ordemRepository).findById(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // ===== patchSaidaEfetiva =====
    @Test
    @DisplayName("patchSaidaEfetiva: Deve atualizar data de saída efetiva com sucesso")
    void testPatchSaidaEfetiva() {
        // Arrange
        LocalDate dataSaidaEfetiva = LocalDate.now().plusWeeks(6);
        OrdemDeServico ordemParaAtualizar = ordemDeServicos;
        ordemParaAtualizar.setDtSaidaEfetiva(dataSaidaEfetiva);

        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemParaAtualizar));
        when(ordemRepository.save(any(OrdemDeServico.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        OrdemDeServico resultado = service.patchSaidaEfetiva(requestPatchSaidaEfetiva);

        // Assert
        assertNotNull(resultado);
        assertEquals(dataSaidaEfetiva, resultado.getDtSaidaEfetiva());
        verify(ordemRepository).findById(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // ===== patchStatus =====
    @Test
    @DisplayName("patchStatus: Deve atualizar status com sucesso")
    void testPatchStatus() {
        // Arrange
        OrdemDeServico ordemParaAtualizar = ordemDeServicos;
        ordemParaAtualizar.setStatus(StatusVeiculo.FINALIZADO);

        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemParaAtualizar));
        when(ordemRepository.save(any(OrdemDeServico.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        OrdemDeServico resultado = service.patchStatus(requestPatchStatus);

        // Assert
        assertNotNull(resultado);
        assertEquals(StatusVeiculo.FINALIZADO, resultado.getStatus());
        verify(ordemRepository).findById(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // ===== patchSeguradora =====
    @Test
    @DisplayName("patchSeguradora: Deve atualizar status de seguradora com sucesso")
    void testPatchSeguradora() {
        // Arrange
        OrdemDeServico ordemParaAtualizar = ordemDeServicos;
        ordemParaAtualizar.setSeguradora(true);

        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemParaAtualizar));
        when(ordemRepository.save(any(OrdemDeServico.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        OrdemDeServico resultado = service.patchSeguradora(requestPatchSeguradora);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.getSeguradora());
        verify(ordemRepository).findById(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // ===== patchNfRealizada =====
    @Test
    @DisplayName("patchNfRealizada: Deve atualizar status de NF com sucesso")
    void testPatchNfRealizada() {
        // Arrange
        OrdemDeServico ordemParaAtualizar = ordemDeServicos;
        ordemParaAtualizar.setNfRealizada(true);

        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemParaAtualizar));
        when(ordemRepository.save(any(OrdemDeServico.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        OrdemDeServico resultado = service.patchNfRealizada(requestPatchNfRealizada);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.getNfRealizada());
        verify(ordemRepository).findById(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // ===== patchPagtoRealizado =====
    @Test
    @DisplayName("patchPagtoRealizado: Deve atualizar status de pagamento com sucesso")
    void testPatchPagtoRealizado() {
        // Arrange
        OrdemDeServico ordemParaAtualizar = ordemDeServicos;
        ordemParaAtualizar.setPagtRealizado(true);

        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemParaAtualizar));
        when(ordemRepository.save(any(OrdemDeServico.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        OrdemDeServico resultado = service.patchPagtoRealizado(requestPatchPagtoRealizado);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.getPagtRealizado());
        verify(ordemRepository).findById(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // ===== deleteOrdem =====
    @Test
    @DisplayName("deleteOrdem: Deve deletar ordem com sucesso quando não possui serviços")
    void testDeleteOrdemComSucesso() {
        // Arrange
        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemDeServicos));
        when(itemServicoService.listarPelaOrdemServico(ordemDeServicos)).thenReturn(List.of());
        doNothing().when(ordemRepository).delete(ordemDeServicos);

        // Act
        assertDoesNotThrow(() -> service.deleteOrdem(1));

        // Assert
        verify(ordemRepository).findById(1);
        verify(itemServicoService).listarPelaOrdemServico(ordemDeServicos);
        verify(ordemRepository).delete(ordemDeServicos);
    }

    @Test
    @DisplayName("deleteOrdem: Deve lançar BadRequestException quando ordem possui serviços")
    void testDeleteOrdemComServicos() {
        // Arrange
        ItemServico itemServico = new ItemServico();
        itemServico.setIdRegistroServico(1);

        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemDeServicos));
        when(itemServicoService.listarPelaOrdemServico(ordemDeServicos)).thenReturn(List.of(itemServico));

        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class,
            () -> service.deleteOrdem(1));

        assertEquals("Não é possível deletar ordem de serviço que possui serviços anexados", exception.getMessage());
        verify(ordemRepository, never()).delete(any(OrdemDeServico.class));
    }

    @Test
    @DisplayName("deleteOrdem: Deve lançar DataNotFoundException quando ordem não existe")
    void testDeleteOrdem_NaoEncontrada() {
        // Arrange
        when(ordemRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class,
            () -> service.deleteOrdem(999));

        verify(ordemRepository).findById(999);
        verify(itemServicoService, never()).listarPelaOrdemServico(any());
        verify(ordemRepository, never()).delete(any());
    }
}
