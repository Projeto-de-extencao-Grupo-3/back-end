package geo.track.service;

import geo.track.domain.ItemServico;
import geo.track.domain.OrdemDeServico;
import geo.track.repository.ItemServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        itemServico.setFkOrdemServico(ordemDeServicos);
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
}
