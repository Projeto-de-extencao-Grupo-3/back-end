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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ItensServicosService")
class ItensServicoServiceTest {

    @Mock
    private ItemServicoRepository repository;

    @InjectMocks
    private ItemServicoService service;

    private OrdemDeServico ordemDeServicos;
    private ItemServico itemServico;

    @BeforeEach
    void setUp() {
        ordemDeServicos = new OrdemDeServico();
        ordemDeServicos.setIdOrdemServico(1);

        itemServico = new ItemServico();
        itemServico.setIdRegistroServico(1);
        itemServico.setFkOrdemServico(ordemDeServicos);
    }

    // --- Testes para listarPelaOrdemServico ---
    @Test
    @DisplayName("listarPelaOrdemServico: Deve retornar uma lista de itens de serviço com sucesso")
    void deveRetornarListaDeItensDeServicoComSucesso() {
        when(repository.findAllByFkOrdemServicoIdOrdemServico(1)).thenReturn(List.of(itemServico));

        List<ItemServico> resultado = service.listarPelaOrdemServico(ordemDeServicos);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(itemServico.getIdRegistroServico(), resultado.get(0).getIdRegistroServico());
        verify(repository).findAllByFkOrdemServicoIdOrdemServico(1);
    }

    @Test
    @DisplayName("listarPelaOrdemServico: Deve retornar uma lista vazia quando não houver itens para a ordem de serviço")
    void deveRetornarListaVaziaQuandoNaoHouverItens() {
        when(repository.findAllByFkOrdemServicoIdOrdemServico(1)).thenReturn(Collections.emptyList());

        List<ItemServico> resultado = service.listarPelaOrdemServico(ordemDeServicos);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findAllByFkOrdemServicoIdOrdemServico(1);
    }
}