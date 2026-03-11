package geo.track.service;

import geo.track.domain.ItemServico;
import geo.track.domain.OrdemDeServico;
import geo.track.enums.Servico;
import geo.track.enums.servico.LadoVeiculo;
import geo.track.enums.servico.ParteVeiculo;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.ItemServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ItemServicoService")
class ItensServicoServiceTest {

    @Mock
    private ItemServicoRepository repository;

    @InjectMocks
    private ItemServicoService service;

    private OrdemDeServico ordemDeServicos;
    private ItemServico itemServico;

    @BeforeEach
    void setUp() {
        // Arrange: Preparar Entidades
        ordemDeServicos = new OrdemDeServico();
        ordemDeServicos.setIdOrdemServico(1);

        itemServico = new ItemServico();
        itemServico.setIdRegistroServico(1);
        itemServico.setPrecoCobrado(100.0);
        itemServico.setParteVeiculo(ParteVeiculo.CAPO);
        itemServico.setLadoVeiculo(LadoVeiculo.COMPLETO);
        itemServico.setCor("Azul");
        itemServico.setEspecificacaoServico("Pintura");
        itemServico.setObservacoesItem("Pequeno arranhão");
        itemServico.setTipoServico(Servico.FUNILARIA);
        itemServico.setFkOrdemServico(ordemDeServicos);
    }

    // ===== cadastrar =====
    @Test
    @DisplayName("cadastrar: Deve cadastrar um novo item de serviço com sucesso")
    void testCadastrarItemServicoComSucesso() {
        // Arrange
        when(repository.save(any(ItemServico.class))).thenAnswer(invocation -> {
            ItemServico savedItem = invocation.getArgument(0);
            savedItem.setIdRegistroServico(1); // Simulate ID generation
            return savedItem;
        });

        // Act
        ItemServico resultado = service.cadastrar(itemServico);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdRegistroServico());
        assertEquals("Pintura", resultado.getEspecificacaoServico());
        verify(repository).save(any(ItemServico.class));
    }

    // ===== listar =====
    @Test
    @DisplayName("listar: Deve retornar lista de itens de serviço quando existem")
    void testListarItensServicoComResultados() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(itemServico));

        // Act
        List<ItemServico> resultado = service.listar();

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(itemServico.getIdRegistroServico(), resultado.get(0).getIdRegistroServico());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("listar: Deve retornar lista vazia quando não existem itens de serviço")
    void testListarItensServicoSemResultados() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act
        List<ItemServico> resultado = service.listar();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findAll();
    }

    // ===== findById =====
    @Test
    @DisplayName("findById: Deve encontrar item de serviço por ID com sucesso")
    void testFindByIdComSucesso() {
        // Arrange
        when(repository.findById(1)).thenReturn(Optional.of(itemServico));

        // Act
        ItemServico resultado = service.findById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdRegistroServico());
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("findById: Deve lançar DataNotFoundException quando ID não existe")
    void testFindByIdNaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.findById(999));

        assertEquals("Item de Serviço não encontrado", exception.getMessage());
        verify(repository).findById(999);
    }

    // ===== listarPelaOrdemServico =====
    @Test
    @DisplayName("listarPelaOrdemServico: Deve retornar lista de itens de serviço quando existem")
    void testListarPelaOrdemServicoComResultados() {
        // Arrange
        when(repository.findAllByFkOrdemServicoIdOrdemServico(1)).thenReturn(List.of(itemServico));

        // Act
        List<ItemServico> resultado = service.listarPelaOrdemServico(ordemDeServicos);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getIdRegistroServico());
        verify(repository).findAllByFkOrdemServicoIdOrdemServico(1);
    }

    @Test
    @DisplayName("listarPelaOrdemServico: Deve retornar lista vazia quando não existem itens")
    void testListarPelaOrdemServicoSemResultados() {
        // Arrange
        when(repository.findAllByFkOrdemServicoIdOrdemServico(1)).thenReturn(List.of());

        // Act
        List<ItemServico> resultado = service.listarPelaOrdemServico(ordemDeServicos);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findAllByFkOrdemServicoIdOrdemServico(1);
    }

    // ===== atualizar =====
    @Test
    @DisplayName("atualizar: Deve atualizar item de serviço com sucesso quando existe")
    void testAtualizarItemServicoComSucesso() {
        // Arrange
        ItemServico updatedItemServico = new ItemServico();
        updatedItemServico.setPrecoCobrado(150.0);
        updatedItemServico.setParteVeiculo(ParteVeiculo.PARACHOQUE);
        updatedItemServico.setLadoVeiculo(LadoVeiculo.ESQUERDO);
        updatedItemServico.setCor("Preto");
        updatedItemServico.setEspecificacaoServico("Reparo");
        updatedItemServico.setObservacoesItem("Amassado leve");
        updatedItemServico.setTipoServico(Servico.PINTURA);
        updatedItemServico.setFkOrdemServico(ordemDeServicos);

        when(repository.findById(1)).thenReturn(Optional.of(itemServico));
        when(repository.save(any(ItemServico.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ItemServico resultado = service.atualizar(1, updatedItemServico);

        // Assert
        assertNotNull(resultado);
        assertEquals(150.0, resultado.getPrecoCobrado());
        assertEquals(ParteVeiculo.PARACHOQUE, resultado.getParteVeiculo());
        assertEquals(LadoVeiculo.ESQUERDO, resultado.getLadoVeiculo());
        assertEquals("Preto", resultado.getCor());
        assertEquals("Reparo", resultado.getEspecificacaoServico());
        assertEquals("Amassado leve", resultado.getObservacoesItem());
        assertEquals(Servico.PINTURA, resultado.getTipoServico());
        verify(repository).findById(1);
        verify(repository).save(any(ItemServico.class));
    }

    @Test
    @DisplayName("atualizar: Deve lançar DataNotFoundException quando item de serviço não existe")
    void testAtualizarItemServicoNaoEncontrado() {
        // Arrange
        ItemServico updatedItemServico = new ItemServico();
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
            () -> service.atualizar(999, updatedItemServico));

        assertEquals("Item de Serviço não encontrado", exception.getMessage());
        verify(repository).findById(999);
        verify(repository, never()).save(any(ItemServico.class));
    }

    // ===== delete =====
    @Test
    @DisplayName("delete: Deve deletar item de serviço com sucesso")
    void testDeletarItemServicoComSucesso() {
        // Arrange
        doNothing().when(repository).deleteById(1);

        // Act & Assert
        assertDoesNotThrow(() -> service.delete(1));

        // Assert
        verify(repository).deleteById(1);
    }
}
