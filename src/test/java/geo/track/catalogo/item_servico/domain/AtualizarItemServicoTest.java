package geo.track.catalogo.item_servico.domain;

import geo.track.catalogo.item_servico.infraestructure.persistence.ItemServicoRepository;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.*;
import geo.track.catalogo.item_servico.infraestructure.request.RequestPutItemServico;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarItemServicoTest {

    @Mock
    private ItemServicoRepository ITEM_SERVICO_REPOSITORY;

    @Mock
    private ItemServicoService ITEM_SERVICO_SERVICE;

    @Mock
    private Log log;

    private AtualizarItemServico useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarItemServico(ITEM_SERVICO_REPOSITORY, ITEM_SERVICO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
        Integer id = 1;
        RequestPutItemServico request = new RequestPutItemServico(
                250.0,
                ParteVeiculo.LATERAL,
                LadoVeiculo.ESQUERDO,
                "Preto Fosco",
                "Reparo lateral esquerda",
                TipoPintura.PARCIAL,
                Servico.PINTURA
        );

        ItemServico itemExistente = new ItemServico();
        itemExistente.setIdRegistroServico(id);
        itemExistente.setPrecoCobrado(200.0);
        itemExistente.setParteVeiculo(ParteVeiculo.CAPO);

        ItemServico itemSalvo = new ItemServico();
        itemSalvo.setIdRegistroServico(id);
        itemSalvo.setPrecoCobrado(250.0);
        itemSalvo.setParteVeiculo(ParteVeiculo.LATERAL);
        itemSalvo.setLadoVeiculo(LadoVeiculo.ESQUERDO);
        itemSalvo.setCor("Preto Fosco");

        when(ITEM_SERVICO_SERVICE.buscarItemServicoPorId(id)).thenReturn(itemExistente);
        when(ITEM_SERVICO_REPOSITORY.save(any(ItemServico.class))).thenReturn(itemSalvo);

        ItemServico resultado = useCase.execute(id, request);

        assertNotNull(resultado);
        assertEquals(250.0, resultado.getPrecoCobrado());
        assertEquals(ParteVeiculo.LATERAL, resultado.getParteVeiculo());
        assertEquals("Preto Fosco", resultado.getCor());
        verify(ITEM_SERVICO_SERVICE).buscarItemServicoPorId(id);
        verify(ITEM_SERVICO_REPOSITORY).save(any(ItemServico.class));
    }

    @Test
    void devePropagateDataNotFoundException_QuandoItemNaoEncontrado() {
        Integer id = 999;
        RequestPutItemServico request = new RequestPutItemServico(
                250.0, null, null, null, null, null, null
        );

        when(ITEM_SERVICO_SERVICE.buscarItemServicoPorId(id))
                .thenThrow(new DataNotFoundException("Item de Serviço não encontrado", Domains.ITEM_SERVICO));

        assertThrows(DataNotFoundException.class, () -> useCase.execute(id, request));
        verify(ITEM_SERVICO_REPOSITORY, never()).save(any());
    }
}
