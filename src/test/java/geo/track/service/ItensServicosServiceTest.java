package geo.track.service;

import geo.track.domain.ItensServicos;
import geo.track.domain.OrdemDeServicos;
import geo.track.repository.ItensServicoRepository;
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
class ItensServicosServiceTest {

    @Mock
    private ItensServicoRepository repository;

    @InjectMocks
    private ItensServicosService service;

    private OrdemDeServicos ordemDeServicos;
    private ItensServicos itemServico;

    @BeforeEach
    void setUp() {
        ordemDeServicos = new OrdemDeServicos();
        ordemDeServicos.setIdOrdemServico(1);

        itemServico = new ItensServicos();
        itemServico.setIdItensServicos(1);
        itemServico.setFkOrdemServico(ordemDeServicos);
    }

    // --- Testes para listarPelaOrdemServico ---
    @Test
    @DisplayName("listarPelaOrdemServico: Deve retornar uma lista de itens de serviço com sucesso")
    void deveRetornarListaDeItensDeServicoComSucesso() {
        when(repository.findAllByFkOrdemServicoIdOrdemServico(1)).thenReturn(List.of(itemServico));

        List<ItensServicos> resultado = service.listarPelaOrdemServico(ordemDeServicos);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(itemServico.getIdItensServicos(), resultado.get(0).getIdItensServicos());
        verify(repository).findAllByFkOrdemServicoIdOrdemServico(1);
    }

    @Test
    @DisplayName("listarPelaOrdemServico: Deve retornar uma lista vazia quando não houver itens para a ordem de serviço")
    void deveRetornarListaVaziaQuandoNaoHouverItens() {
        when(repository.findAllByFkOrdemServicoIdOrdemServico(1)).thenReturn(Collections.emptyList());

        List<ItensServicos> resultado = service.listarPelaOrdemServico(ordemDeServicos);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findAllByFkOrdemServicoIdOrdemServico(1);
    }
}