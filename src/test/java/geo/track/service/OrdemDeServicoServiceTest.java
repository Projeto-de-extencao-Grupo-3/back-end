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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do OrdemDeServicosService")
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
        registroEntrada = new RegistroEntrada();
        registroEntrada.setIdRegistroEntrada(1);

        ordemDeServicos = OrdemDeServico.builder()
                .idOrdemServico(1)
                .valorTotal(500.0)
                .status(StatusVeiculo.EM_PRODUCAO)
                .nfRealizada(true)
                .dtSaidaPrevista(LocalDate.now().plusMonths(1))
                .fkEntrada(registroEntrada)
                .build();

        postEntradaVeiculo = new PostEntradaVeiculo(StatusVeiculo.EM_PRODUCAO, 500.0, 1);
        requestPutValorESaida = new RequestPutValorESaida(1, 750.0, LocalDate.now().plusWeeks(5), 1);
        requestPatchSaidaEfetiva = new RequestPatchSaidaEfetiva(1, 1, LocalDate.now().plusWeeks(6));
        requestPatchStatus = new RequestPatchStatus(1, StatusVeiculo.FINALIZADO, 1);
        requestPatchSeguradora = new RequestPatchSeguradora(1, 1, true);
        requestPatchNfRealizada = new RequestPatchNfRealizada(1, 1, true);
        requestPatchPagtoRealizado = new RequestPatchPagtoRealizado(1, 1, true);
    }

    // --- Testes para postOrdem ---
    @Test
    @DisplayName("postOrdem: Deve criar uma ordem de serviço com sucesso")
    void deveCriarOrdemDeServicoComSucesso() {
        when(registroEntradaService.buscarEntradaPorId(1)).thenReturn(registroEntrada);
        when(ordemRepository.save(any(OrdemDeServico.class))).thenReturn(ordemDeServicos);

        OrdemDeServico resultado = service.cadastrarOrdemServico(postEntradaVeiculo);

        assertNotNull(resultado);
        assertEquals(postEntradaVeiculo.getValorTotal(), resultado.getValorTotal());
        verify(registroEntradaService).buscarEntradaPorId(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // --- Testes para findOrdem ---
    @Test
    @DisplayName("findOrdem: Deve retornar uma lista de ordens de serviço")
    void deveRetornarListaDeOrdens() {
        when(ordemRepository.findAll()).thenReturn(List.of(ordemDeServicos));
        List<OrdemDeServico> resultado = service.listarOrdensServico();
        assertFalse(resultado.isEmpty());
        verify(ordemRepository).findAll();
    }

    // --- Testes para findOrdemById ---
    @Test
    @DisplayName("findOrdemById: Deve encontrar ordem por ID com sucesso")
    void deveEncontrarOrdemPorIdComSucesso() {
        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemDeServicos));
        OrdemDeServico resultado = service.buscarOrdemServicoPorId(1);
        assertNotNull(resultado);
        verify(ordemRepository).findById(1);
    }

    // --- Testes para putValorESaida ---
    @Test
    @DisplayName("putValorESaida: Deve atualizar valor e data de saída com sucesso")
    void deveAtualizarValorESaidaComSucesso() {
        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemDeServicos));
        when(ordemRepository.save(any(OrdemDeServico.class))).thenAnswer(inv -> inv.getArgument(0));

        OrdemDeServico resultado = service.atualizarValorESaida(requestPutValorESaida);

        assertEquals(requestPutValorESaida.getValorTotal(), resultado.getValorTotal());
        assertEquals(requestPutValorESaida.getSaidaPrevista(), resultado.getDataSaidaPrevista());
        verify(ordemRepository).findById(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // --- Testes para patchSaidaEfetiva ---
    @Test
    @DisplayName("patchSaidaEfetiva: Deve atualizar a data de saída efetiva com sucesso")
    void deveAtualizarSaidaEfetivaComSucesso() {
        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemDeServicos));
        when(ordemRepository.save(any(OrdemDeServico.class))).thenAnswer(inv -> inv.getArgument(0));
        OrdemDeServico resultado = service.atualizarSaidaEfetiva(requestPatchSaidaEfetiva);
        assertEquals(requestPatchSaidaEfetiva.getDtSaidaEfeiva(), resultado.getDataSaidaEfetiva());
        verify(ordemRepository).findById(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // --- Testes para patchStatus ---
    @Test
    @DisplayName("patchStatus: Deve atualizar o status com sucesso")
    void deveAtualizarStatusComSucesso() {
        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemDeServicos));
        when(ordemRepository.save(any(OrdemDeServico.class))).thenAnswer(inv -> inv.getArgument(0));
        OrdemDeServico resultado = service.atualizarStatus(requestPatchStatus);
        assertEquals(requestPatchStatus.getStatus(), resultado.getStatus());
        verify(ordemRepository).findById(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // --- Testes para patchSeguradora ---
    @Test
    @DisplayName("patchSeguradora: Deve atualizar o status da seguradora com sucesso")
    void deveAtualizarSeguradoraComSucesso() {
        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemDeServicos));
        when(ordemRepository.save(any(OrdemDeServico.class))).thenAnswer(inv -> inv.getArgument(0));
        OrdemDeServico resultado = service.atualizarSeguradora(requestPatchSeguradora);
        assertEquals(requestPatchSeguradora.getSeguradora(), resultado.getSeguradora());
        verify(ordemRepository).findById(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // --- Testes para patchNfRealizada ---
    @Test
    @DisplayName("patchNfRealizada: Deve atualizar o status da NF com sucesso")
    void deveAtualizarNfRealizadaComSucesso() {
        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemDeServicos));
        when(ordemRepository.save(any(OrdemDeServico.class))).thenAnswer(inv -> inv.getArgument(0));
        OrdemDeServico resultado = service.atualizarNotaFiscalRealizada(requestPatchNfRealizada);
        assertEquals(requestPatchNfRealizada.getNfRealizada(), resultado.getNfRealizada());
        verify(ordemRepository).findById(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // --- Testes para patchPagtoRealizado ---
    @Test
    @DisplayName("patchPagtoRealizado: Deve atualizar o status do pagamento com sucesso")
    void deveAtualizarPagtoRealizadoComSucesso() {
        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemDeServicos));
        when(ordemRepository.save(any(OrdemDeServico.class))).thenAnswer(inv -> inv.getArgument(0));
        OrdemDeServico resultado = service.atualizarPagamentoRealizado(requestPatchPagtoRealizado);
        assertEquals(requestPatchPagtoRealizado.getPagtoRealizado(), resultado.getPagtRealizado());
        verify(ordemRepository).findById(1);
        verify(ordemRepository).save(any(OrdemDeServico.class));
    }

    // --- Testes para deleteOrdem ---
    @Test
    @DisplayName("deleteOrdem: Deve deletar uma ordem de serviço com sucesso")
    void deveDeletarOrdemComSucesso() {
        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemDeServicos));
        when(itemServicoService.listarPelaOrdemServico(ordemDeServicos)).thenReturn(Collections.emptyList());
        doNothing().when(ordemRepository).delete(ordemDeServicos);

        assertDoesNotThrow(() -> service.deletarOrdemServico(1));

        verify(ordemRepository).findById(1);
        verify(itemServicoService).listarPelaOrdemServico(ordemDeServicos);
        verify(ordemRepository).delete(ordemDeServicos);
    }

    @Test
    @DisplayName("deleteOrdem: Deve lançar BadRequestException ao tentar deletar ordem com serviços atrelados")
    void deveLancarExcecaoAoDeletarOrdemComServicos() {
        when(ordemRepository.findById(1)).thenReturn(Optional.of(ordemDeServicos));
        when(itemServicoService.listarPelaOrdemServico(ordemDeServicos)).thenReturn(List.of(new ItemServico()));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            service.deletarOrdemServico(1);
        });

        assertEquals("Não é possível deletar ordem de serviço que possui serviços anexados", exception.getMessage());
        verify(ordemRepository, never()).delete(any(OrdemDeServico.class));
    }

    @Test
    @DisplayName("deleteOrdem: Deve lançar DataNotFoundException ao tentar deletar ordem inexistente")
    void deveLancarExcecaoAoDeletarOrdemInexistente() {
        when(ordemRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> service.deletarOrdemServico(99));

        verify(ordemRepository).findById(99);
        verify(itemServicoService, never()).listarPelaOrdemServico(any());
        verify(ordemRepository, never()).delete(any());
    }
}